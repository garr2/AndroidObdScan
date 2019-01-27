package com.innowise_group.androidobdscan

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.exceptions.UnableToConnectException
import com.innowise_group.androidobdscan.app.ObdScanApplication
import com.innowise_group.androidobdscan.presentation.adqapter.ObdDataAdapter
import com.innowise_group.domain.useCase.BtUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException
import javax.inject.Inject
import kotlin.Exception


class MainActivity : AppCompatActivity() {

    companion object {
        const val BT_REQUEST_CODE = 1
    }

    @Inject
    lateinit var btDataUseCase: BtUseCase

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private lateinit var deviceList: Set<BluetoothDevice>
    private var deviceStrs = ArrayList<String>()
    private val devices = ArrayList<String>()
    private var deviceAddress: String = ""
    private val obdDataList = HashMap<String, String>()
    private var obdDataArr = ArrayList<String>()
    private val adapter = ObdDataAdapter()

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        ObdScanApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Crashlytics.log("MESAGE")

        obdDataArr = obdDataList.transformToArray(obdDataArr)
        setAdapter(obdDataArr)

        btnResetTroubles.setOnClickListener {
            val disposable = btDataUseCase.resetTroubleCodes()
                .subscribeBy(
                    onNext = {
                        Toast.makeText(
                            this, "Troubles reset",
                            Toast.LENGTH_SHORT
                        ).show()
                        getAllData()
                    },
                    onError = {

                    }
                )
            addToDisposable(disposable)
        }

        btnDynamicRpm.setOnClickListener {
            handler.post(dynamicRpm)
        }
    }

    override fun onStart() {
        super.onStart()
        getBluetoothState()
    }

    override fun onDestroy() {
        handler.removeCallbacks(dynamicRpm)
        btDataUseCase.cancel()
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun getBluetoothState() {
        val disposable = btDataUseCase.getState()
            .subscribeBy(
                onNext = {
                    if (it.isEnabled) {
                        initDeviceList()
                    } else requestBluetoothActivation()
                },
                onError = {
                    Toast.makeText(
                        this,
                        "Error: bluetooth is not available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        addToDisposable(disposable)
    }

    private fun requestBluetoothActivation() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBtIntent, BT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                initDeviceList()
            } else {
                Toast.makeText(
                    this,
                    "The application can't work without bluetooth",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    private fun initDeviceList() {
        val disposable = btDataUseCase.getDeviceList().subscribeBy(
            onNext = {
                Log.d("myLog", it.toString())
                deviceList = it
                if (deviceList.isNotEmpty())
                    showBtListDialog()
            },
            onError = {
                Log.d("myLog", it.toString())
            }
        )

        addToDisposable(disposable)
    }

    private fun showBtListDialog() {
        deviceStrs.clear()
        for (device in deviceList) {
            deviceStrs.add(device.name)
            devices.add(device.address)
        }

        val alertDialog = AlertDialog.Builder(this)

        val adapter = ArrayAdapter<String>(
            this, android.R.layout.select_dialog_singlechoice,
            deviceStrs.toArray(arrayOfNulls<String>(deviceStrs.size))
        )

        if (deviceList.isNotEmpty()) {
            alertDialog.setTitle("Choose Bluetooth device")
            alertDialog.setSingleChoiceItems(adapter, -1) { dialog, _ ->
                dialog.dismiss()
                val position = (dialog as AlertDialog).listView.checkedItemPosition
                deviceAddress = devices[position]
                connectToDevice()
            }
        } else {
            alertDialog.setTitle("Attention")
            alertDialog.setMessage("If adapter is not synchronized with phone you must synchronize manually.")
        }

        alertDialog.setNeutralButton("Bluetooth settings") { dialog, _ ->
            dialog.dismiss()
            val intentOpenBluetoothSettings = Intent()
            intentOpenBluetoothSettings.action = android.provider.Settings.ACTION_BLUETOOTH_SETTINGS
            startActivity(intentOpenBluetoothSettings)
        }

        alertDialog.show()

    }

    private fun connectToDevice() {
        val disposable = btDataUseCase.connect(deviceAddress).subscribeBy(
            onComplete = {
                getAllData()
                //getDynamicRpm()
                btnDynamicRpm.isClickable = true
            },
            onError = {
                Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                Log.d("myLog", "Connection failed, error: $it")
                //Crashlytics.logException(it)
            }
        )
        addToDisposable(disposable)
    }

    private var counter = 0

    private fun getAllData() {

        try {
            val disposable = btDataUseCase.getAllBtData()
                .subscribeBy(
                    onNext = {
                        obdDataList.putAll(it)
                        notifyAdapter(obdDataList.transformToArray(obdDataArr))
                        Crashlytics.logException(Exception(it.toString()))
                        tvScanResult.text = it.toString()
                    },
                    onError = {
                        Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                        Log.d("myLog", "Connection failed, error: $it")
                        Crashlytics.logException(it)
                        tvScanResult.text = it.toString()
                    }
                )
            addToDisposable(disposable)


            addToDisposable(btDataUseCase.getControllCommands()
                .subscribeBy(
                    onNext = {
                        obdDataList.putAll(it)
                        notifyAdapter(obdDataList.transformToArray(obdDataArr))
                        Crashlytics.logException(Exception(it.toString()))
                    },
                    onError = {
                        Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                        Log.d("myLog", "Connection failed, error: $it")
                        Crashlytics.logException(it)
                    }
                ))

            addToDisposable(btDataUseCase.getEngineCommands()
                .subscribeBy(
                    onNext = {
                        obdDataList.putAll(it)
                        notifyAdapter(obdDataList.transformToArray(obdDataArr))
                        Crashlytics.logException(Exception(it.toString()))
                    },
                    onError = {
                        Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                        Log.d("myLog", "Connection failed, error: $it")
                        Crashlytics.logException(it)
                    }
                ))

            addToDisposable(btDataUseCase.getFuelCommands()
                .subscribeBy(
                    onNext = {
                        obdDataList.putAll(it)
                        notifyAdapter(obdDataList.transformToArray(obdDataArr))
                        Crashlytics.logException(Exception(it.toString()))
                    },
                    onError = {
                        Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                        Log.d("myLog", "Connection failed, error: $it")
                        Crashlytics.logException(it)
                    }
                ))

            addToDisposable(btDataUseCase.getPreashureCommands().subscribeBy(
                onNext = {
                    obdDataList.putAll(it)
                    notifyAdapter(obdDataList.transformToArray(obdDataArr))
                    Crashlytics.logException(Exception(it.toString()))
                },
                onError = {
                    Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                    Log.d("myLog", "Connection failed, error: $it")
                    Crashlytics.logException(it)
                }
            ))

            addToDisposable(btDataUseCase.getProtocolCommands()
                .subscribeBy(
                    onNext = {
                        obdDataList.putAll(it)
                        notifyAdapter(obdDataList.transformToArray(obdDataArr))
                        Crashlytics.logException(Exception(it.toString()))
                    },
                    onError = {
                        Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                        Log.d("myLog", "Connection failed, error: $it")
                        Crashlytics.logException(it)
                    }
                ))

            addToDisposable(btDataUseCase.getTemperatureCommands()
                .subscribeBy(
                    onNext = {
                        obdDataList.putAll(it)
                        notifyAdapter(obdDataList.transformToArray(obdDataArr))
                        Crashlytics.logException(Exception(it.toString()))
                    },
                    onError = {
                        Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                        Log.d("myLog", "Connection failed, error: $it")
                        Crashlytics.logException(it)
                    }
                ))
            Crashlytics.log("MESAGE")
        } catch (uc: UnableToConnectException) {
            Crashlytics.logException(uc)
            showUnableConnectErrorDialog()
        } catch (e: Throwable) {
            e.printStackTrace()
            if (counter <= 4) {
                counter++
                getAllData()
            } else {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                Crashlytics.logException(e)
            }
        }

    }

    private fun showUnableConnectErrorDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Error")
        dialog.setMessage("OBD2 can't to connect with ECU")
        dialog.setPositiveButton("ok") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        dialog.show()
    }

    var dynamicRpm: Runnable = object : Runnable {
        override fun run() {
            try {
                getDynamicRpm()
                handler.postDelayed(this, 1000)
            } catch (e: Throwable) {
                e.printStackTrace()
                Crashlytics.logException(e)
            }

        }
    }

    private fun getDynamicRpm() {
        addToDisposable(btDataUseCase.getDynamicRpm()
            .subscribeBy(
                onNext = {
                    obdDataList.putAll(it)
                    notifyAdapter(obdDataList.transformToArray(obdDataArr))
                    btnDynamicRpm.text = it.toString()
                },
                onError = {
                    Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                    Log.d("myLog", "Connection failed, error: $it")
                    Crashlytics.logException(it)
                }
            ))
    }

    private fun Map<String, String>.transformToArray(arr: ArrayList<String>): ArrayList<String> {
        if (this.isNotEmpty()) {
            Crashlytics.logException(NullPointerException(this.toString()))
        }
        arr.clear()
        var counter = 0
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            arr[counter] = iterator.next().value
            counter++
        }
        return arr
    }

    private fun setAdapter(arr: ArrayList<String>) {
        Crashlytics.logException(Exception(arr.toString()))

        adapter.obdDataList = arr
        lvBtList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        lvBtList.adapter = adapter
    }

    private fun notifyAdapter(arr: ArrayList<String>) {
        adapter.obdDataList = arr
        adapter.notifyDataSetChanged()
    }

    private fun addToDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /*private var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var str = ""
    private fun startScan(){
        bluetoothAdapter.startLeScan (callback)
    }

    private fun stopScan(){
        bluetoothAdapter.stopLeScan(callback)
    }

    private val callback = BluetoothAdapter.LeScanCallback{
        device: BluetoothDevice, _: Int, _: ByteArray ->
        Toast.makeText(this, "Device found: ${device.toString()} + ${device.name}"
                ,Toast.LENGTH_SHORT).show()
        str = "Device found: ${device.toString()} + ${device.name}\n"
        tvScanResult.text = str
    }

    private class BluetoothReceiver: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            Log.d("myLog", action)

            if (action == BluetoothDevice.ACTION_FOUND){
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                Log.d("myLog", device.name)
                Toast.makeText(context, "Device found: ${device.name}", Toast.LENGTH_LONG).show()
            }else if (action == BluetoothAdapter.ACTION_DISCOVERY_FINISHED){
                Toast.makeText(context, "Discovery finished", Toast.LENGTH_LONG).show()
            }
        }

    }*/
}

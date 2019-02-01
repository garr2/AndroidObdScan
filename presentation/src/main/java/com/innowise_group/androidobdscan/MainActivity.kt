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
import com.innowise_group.androidobdscan.presentation.adapter.ObdDataAdapter
import com.innowise_group.domain.useCase.BtUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    companion object {
        const val BT_REQUEST_CODE = 1

        const val CONTROLL_DATA = "Control data"
        const val ENGINE_DATA = "Engine data"
        const val FUEL_DATA = "Fuel data"
        const val POWER_DATA = "Power data"
        const val PRESSURE_DATA = "Pressure data"
        const val TEMPERATURE_DATA = "Temperature data"
        const val TROUBLE_CODES = "Trouble codes"
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
    private var pevDataArrList = ""
    private val adapter = ObdDataAdapter()

    private val obdDataList = HashMap<String, ArrayList<String>>()
    private val controlDataList = ArrayList<String>()
    private val engineDataList = ArrayList<String>()
    private val fuelDataList = ArrayList<String>()
    private val powerDataList = ArrayList<String>()
    private val pressureDataList = ArrayList<String>()
    private val temperatureDataList = ArrayList<String>()
    private val troubleCodesList = ArrayList<String>()

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        ObdScanApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdapter(obdDataList.transformToArray())

        btnResetTroubles.setOnClickListener {
            val disposable = btDataUseCase.resetTroubleCodes()
                    .subscribeBy(
                            onNext = {
                                Toast.makeText(
                                        this, "Troubles reset",
                                        Toast.LENGTH_SHORT
                                ).show()
                                getAndShowAllData()
                            },
                            onError = {

                            }
                    )
            addToDisposable(disposable)
        }
    }

    override fun onStart() {
        super.onStart()
        getBluetoothState()
        //setTestData()
    }

    override fun onDestroy() {
        handler.removeCallbacks(dynamicRpm)
        handler.removeCallbacks(dynamicData)
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
                    getAndShowAllData()
                },
                onError = {
                    Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                    Log.d("myLog", "Connection failed, error: $it")
                    //Crashlytics.logException(it)
                }
        )
        addToDisposable(disposable)
    }

    private fun getAndShowAllData() {
        try {
            when (pevDataArrList) {
                "" -> getControlData()
                CONTROLL_DATA -> getEngineData()
                ENGINE_DATA -> getFuelData()
                FUEL_DATA -> getPowerData()
                POWER_DATA -> getPressureData()
                PRESSURE_DATA -> getTemperatureData()
                TEMPERATURE_DATA -> getTroubleCodes()
                TROUBLE_CODES -> {
                    pevDataArrList = ""
                    handler.post(dynamicData)
                    handler.post(dynamicRpm)
                }
            }
        }catch (t: Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun notifyData() {
        try {
            when (pevDataArrList) {
                "" -> getEngineData()
                ENGINE_DATA -> getFuelData()
                FUEL_DATA -> getPowerData()
                POWER_DATA -> getPressureData()
                PRESSURE_DATA -> getTemperatureData()
                TEMPERATURE_DATA -> {
                    pevDataArrList = ""
                    handler.post(dynamicData)
                }
            }
        }catch (t: Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun getControlData() {
        controlDataList.clear()
        controlDataList.add(CONTROLL_DATA)
        try {
            addToDisposable(btDataUseCase.getControlData()
                    .subscribeBy(
                            onNext = {
                                controlDataList.add(it)
                                obdDataList[CONTROLL_DATA] = controlDataList
                                notifyAdapter(obdDataList.transformToArray())
                            }, onError = {
                        Crashlytics.logException(it)
                    },
                            onComplete = {
                                pevDataArrList = CONTROLL_DATA

                                getAndShowAllData()
                            }
                    ))
        }catch (t: Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun getEngineData() {
        engineDataList.clear()
        engineDataList.add(ENGINE_DATA)
        try {
            addToDisposable(btDataUseCase.getEngineData()
                    .subscribeBy(
                            onNext = {
                                engineDataList.add(it)
                                obdDataList[ENGINE_DATA] = engineDataList
                                notifyAdapter(obdDataList.transformToArray())
                            }, onError = {
                        Crashlytics.logException(it)
                    },
                            onComplete = {
                                pevDataArrList = ENGINE_DATA
                                getAndShowAllData()
                            }
                    ))
        }catch (t:Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun getFuelData() {
        fuelDataList.clear()
        fuelDataList.add(FUEL_DATA)
        try {
            addToDisposable(btDataUseCase.getFuelData()
                    .subscribeBy(
                            onNext = {
                                fuelDataList.add(it)
                                obdDataList[FUEL_DATA] = fuelDataList
                                notifyAdapter(obdDataList.transformToArray())
                            }, onError = {
                        Crashlytics.logException(it)
                    },
                            onComplete = {
                                pevDataArrList = FUEL_DATA
                                getAndShowAllData()
                            }
                    ))
        }catch (t: Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun getPowerData() {
        powerDataList.clear()
        powerDataList.add(POWER_DATA)
        try {
            addToDisposable(btDataUseCase.getPowerData()
                    .subscribeBy(
                            onNext = {
                                powerDataList.add(it)
                                obdDataList[POWER_DATA] = powerDataList
                                notifyAdapter(obdDataList.transformToArray())
                            }, onError = {
                        Crashlytics.logException(it)
                    },
                            onComplete = {
                                pevDataArrList = POWER_DATA
                                getAndShowAllData()
                            }
                    ))
        }catch (t: Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun getPressureData() {
        pressureDataList.clear()
        pressureDataList.add(PRESSURE_DATA)
        try {
            addToDisposable(btDataUseCase.getPressureData()
                    .subscribeBy(
                            onNext = {
                                pressureDataList.add(it)
                                obdDataList[PRESSURE_DATA] = pressureDataList
                                notifyAdapter(obdDataList.transformToArray())
                            }, onError = {
                        Crashlytics.logException(it)
                    },
                            onComplete = {
                                pevDataArrList = PRESSURE_DATA
                                getAndShowAllData()
                            }
                    ))
        }catch (t: Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun getTemperatureData() {
        temperatureDataList.clear()
        temperatureDataList.add(TEMPERATURE_DATA)
        try {
            addToDisposable(btDataUseCase.getTemperatureData()
                    .subscribeBy(
                            onNext = {
                                temperatureDataList.add(it)
                                obdDataList[TEMPERATURE_DATA] = temperatureDataList
                                notifyAdapter(obdDataList.transformToArray())
                            }, onError = {
                        Crashlytics.logException(it)
                    },
                            onComplete = {
                                pevDataArrList = TEMPERATURE_DATA
                                getAndShowAllData()
                            }
                    ))
        }catch (t: Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun getTroubleCodes() {
        troubleCodesList.clear()
        troubleCodesList.add(TROUBLE_CODES)
        try {
            addToDisposable(btDataUseCase.getTroubleCodesData()
                    .subscribeBy(
                            onNext = {
                                troubleCodesList.add(it)
                                obdDataList[TROUBLE_CODES] = troubleCodesList
                                notifyAdapter(obdDataList.transformToArray())
                            }, onError = {
                        Crashlytics.logException(it)
                    },
                            onComplete = {
                                pevDataArrList = TROUBLE_CODES
                                getAndShowAllData()
                            }
                    ))
        }catch (t: Throwable){
            t.printStackTrace()
            Crashlytics.logException(t)
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
                handler.postDelayed(this, 500)
            } catch (e: Throwable) {
                e.printStackTrace()
                Crashlytics.logException(e)
            }

        }
    }

    var dynamicData: Runnable = Runnable {
        try {
            notifyData()
        } catch (t: Throwable) {
            t.printStackTrace()
            Crashlytics.logException(t)
        }
    }

    private fun getDynamicRpm() {
        addToDisposable(btDataUseCase.getDynamicRpm()
                .subscribeBy(
                        onNext = {
                            tvRpm.text = it
                        },
                        onError = {
                            Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                            Log.d("myLog", "Connection failed, error: $it")
                            Crashlytics.logException(it)
                        }
                ))

        val disposable = btDataUseCase.getSpeedData()
                .subscribeBy(
                        onNext = {
                            tvSpeed.text = it
                        },
                        onError = {
                            if (it is UnableToConnectException) throw it
                            Toast.makeText(this, "Connection failed, error: $it", Toast.LENGTH_SHORT).show()
                            Log.d("myLog", "Connection failed, error: $it")
                            Crashlytics.logException(it)
                        }
                )
        addToDisposable(disposable)
    }

    private fun Map<String, ArrayList<String>>.transformToArray(): ArrayList<String> {
        val list = ArrayList<String>()
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            list.addAll(iterator.next().value)
        }
        Log.d("myLog", list.toString())
        return list
    }

    private fun setAdapter(arr: ArrayList<String>) {
        adapter.obdDataList = arr
        lvBtList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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

    private fun setTestData() {
        addToDisposable(btDataUseCase.getTestData()
                .subscribeBy(
                        onNext = {
                            notifyAdapter(it.transformToArray())
                        },
                        onError = {

                        }
                )
        )
    }
}

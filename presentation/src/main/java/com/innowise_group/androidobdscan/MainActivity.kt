package com.innowise_group.androidobdscan

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.innowise_group.androidobdscan.app.ObdScanApplication
import com.innowise_group.domain.useCase.BtUseCase
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var btDataUseCase: BtUseCase

    private lateinit var deviceList: Set<BluetoothDevice>
    private var deviceStrs = ArrayList<String>()
    private val devices = ArrayList<String>()
    private var deviceAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        ObdScanApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = btDataUseCase.getDeviceList().subscribeBy(
            onNext = {
                Log.d("myLog", it.toString())
                deviceList = it
                if (deviceList.isNotEmpty())
                    setDialog()
            },
            onError = {
                Log.d("myLog", it.toString())
            }
        )
    }

    private fun setDialog() {
        for (device in deviceList) {
            deviceStrs.add(device.name + "\n" + device.address)
            devices.add(device.address)
        }

        val alertDialog = AlertDialog.Builder(this)

        val adapter = ArrayAdapter<String>(
            this, android.R.layout.select_dialog_singlechoice,
            deviceStrs.toArray(arrayOfNulls<String>(deviceStrs.size))
        )

        alertDialog.setSingleChoiceItems(adapter, -1) { dialog, which ->
            dialog.dismiss()
            val position = (dialog as AlertDialog).listView.checkedItemPosition
            deviceAddress = devices[position]
        }

        alertDialog.setTitle("Choose Bluetooth device")
        alertDialog.show()
    }
}

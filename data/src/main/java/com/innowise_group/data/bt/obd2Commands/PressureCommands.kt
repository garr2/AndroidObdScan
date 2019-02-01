package com.innowise_group.data.bt.obd2Commands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.innowise_group.data.bt.EXCEPTION
import com.innowise_group.data.bt.NODATA
import io.github.macfja.obd2.Commander
import io.github.macfja.obd2.command.livedata.*
import io.github.macfja.obd2.elm327.response.NoDataResponse
import io.github.macfja.obd2.response.PressureResponse
import io.reactivex.Observable

class PressureCommands(private val commander: Commander) {

    fun getAllData(): Observable<String>{
return Observable.just(
    getIntakeManifoldAbsolutePressure()/*,
    getSystemVaporPressure(),
    getAbsoluteBarometricPressure(),
    getAbsoluteEvaSystemVaporPressure()*/
)
    }

    fun getIntakeManifoldAbsolutePressure(): String{
        return try {
            val resp = commander.sendCommand(IntakeManifoldAbsolutePressure())
            if (resp is PressureResponse){
                Crashlytics.logException(Exception(resp.formattedString))
                resp.formattedString
            }else{
                "IntakeManifoldAbsolutePressure: $NODATA"
            }
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "IntakeManifoldAbsolutePressure: $NODATA"
        }catch (e: Exception){
            Crashlytics.logException(e)
            e.printStackTrace()

            "IntakeManifoldAbsolutePressure: $EXCEPTION $e"
        }
    }

    fun getSystemVaporPressure(): String{
        return try {
            commander.sendCommand(EvapSystemVaporPressure()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "SystemVaporPressure: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "SystemVaporPressure: $EXCEPTION $e"
        }
    }

    fun getAbsoluteBarometricPressure(): String{
        return try {
            commander.sendCommand(BarometricPressure()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "AbsoluteBarometricPressure: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "AbsoluteBarometricPressure: $EXCEPTION $e"
        }
    }

    fun getAbsoluteEvaSystemVaporPressure(): String{
        return try {
            commander.sendCommand(EvapSystemVaporAbsolutePressure()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "AbsoluteEvaSystemVaporPressure: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "AbsoluteEvaSystemVaporPressure: $EXCEPTION $e"
        }
    }
}
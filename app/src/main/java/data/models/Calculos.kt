package data.models

class Calculos {

    fun calcularIVA(monto: Float): Float {
        return monto * 0.12f
    }

    fun calcularComision(monto: Float): Float {
        return monto * 0.10f
    }

}
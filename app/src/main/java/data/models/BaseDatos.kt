package data.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "burbuja.db"
        private const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tabla usuario
        val crearTablaUsuarios = """
           CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                usuario TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                tipo TEXT NOT NULL,
                fechaCreacion TEXT NOT NULL
           ) 
        """
        db.execSQL(crearTablaUsuarios)

        // Tabla registros
        val crearTablaRegistros = """
            CREATE TABLE registros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                costoIva REAL NOT NULL,
                comision REAL NOT NULL,
                fechaRegistro TEXT NOT NULL,
                servicios TEXT NOT NULL
            )
        """
        db.execSQL(crearTablaRegistros)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS registros")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }

    fun insertarRegistros(costoIva: Float, comision: Float, fechaRegistro: String, servicios: String): Long {
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put("costoIva", costoIva)
        valores.put("comision", comision)
        valores.put("fechaRegistro", fechaRegistro)
        valores.put("servicios", servicios)
        val id = db.insert("registros", null, valores)
        db.close()
        return id
    }

    fun obtenerRegistros(): List<Registro> {
        val listaRegistros = mutableListOf<Registro>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM registros", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val costoIva = cursor.getFloat(cursor.getColumnIndex("costoIva"))
                val comision = cursor.getFloat(cursor.getColumnIndex("comision"))
                val fechaRegistro = cursor.getString(cursor.getColumnIndex("fechaRegistro"))
                val servicios = cursor.getString(cursor.getColumnIndex("servicios"))

                val registro = Registro(id, costoIva, comision, fechaRegistro, servicios)
                listaRegistros.add(registro)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaRegistros
    }

    fun insertarUsuario(nombre: String, usuario: String, password: String, tipo: String, fechaCreacion: String): Long {
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put("nombre", nombre)
        valores.put("usuario", usuario)
        valores.put("password", password)
        valores.put("tipo", tipo)
        valores.put("fechaCreacion", fechaCreacion)
        val id = db.insert("usuarios", null, valores)
        db.close()
        return id
    }

    fun obtenerUsuarios(): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM usuarios", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val usuario = cursor.getString(cursor.getColumnIndex("usuario"))
                val password = cursor.getString(cursor.getColumnIndex("password"))
                val tipo = cursor.getString(cursor.getColumnIndex("tipo"))
                val fechaCreacion = cursor.getString(cursor.getColumnIndex("fecha_creacion"))
                listaUsuarios.add(Usuario(id, nombre, usuario, password, tipo, fechaCreacion))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaUsuarios
    }
}

// Modelo Registros
data class Registro(val id: Int, val costoIva: Float, val comision: Float, val fechaRegistro: String, val servicios: String)

// Modelo de Usuario
data class Usuario(val id: Int, val nombre: String, val usuario: String, val password: String, val tipo: String, val fechaCreacion: String)
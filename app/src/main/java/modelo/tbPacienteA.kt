package modelo

data class tbPacienteA(
    var UUID_Paciente: String,
    var Nombres: String,
    var Apellidos: String,
    var Edad: Int,
    var Enfermedad: String,
    var Número_habitacion: Int,
    var Número_cama: Int,
    var Fecha_nacimiento: String
)

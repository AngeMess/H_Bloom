CREATE TABLE tbPacienteA(

    UUID_Paciente varchar2(50) primary key,
    Nombres varchar2(80),
    Apellidos varchar2(80),
    Edad number,
    Enfermedad varchar2(50),
    Número_habitacion number,
    Número_cama number,
    Fecha_nacimiento varchar2(50),
    UUID_Medicamento varchar2(50),
    foreign key (UUID_Medicamento) references tbMedicamentosA(UUID_Medicamento)
    
);

select * from tbPacienteA;

CREATE TABLE tbMedicamentosA(

    UUID_Medicamento varchar2(50) primary key,
    Nombre_medicamento varchar2(50),
    Hora_aplicacion varchar2(50)
    
);

select * from tbMedicamentosA;

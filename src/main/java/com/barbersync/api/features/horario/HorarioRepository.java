package com.barbersync.api.features.horario;

import com.barbersync.api.features.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    List<Horario> findByIdBarbero(Usuario barbero);

    List<Horario> findByHoraEntrada(LocalTime horaEntrada);

    List<Horario> findByHoraSalida(LocalTime horaSalida);

    List<Horario> findByHoraEntradaBefore(LocalTime hora);

    List<Horario> findByHoraSalidaAfter(LocalTime hora);

    List<Horario> findByIdBarberoAndHoraEntradaBefore(Usuario barbero, LocalTime hora);

    List<Horario> findByIdBarberoAndHoraSalidaAfter(Usuario barbero, LocalTime hora);
}

package com.therumbling.staymap.concerts.domain.services;

import com.therumbling.staymap.concerts.domain.model.aggregates.Concert;
import com.therumbling.staymap.concerts.domain.model.commands.CancelAttendanceCommand;
import com.therumbling.staymap.concerts.domain.model.commands.ConfirmAttendanceCommand;
import com.therumbling.staymap.concerts.domain.model.commands.CreateConcertCommand;
import com.therumbling.staymap.concerts.domain.model.commands.DeleteConcertCommand;

public interface ConcertCommandService {
  Concert handle(CreateConcertCommand command);

  void handle(ConfirmAttendanceCommand command);

  void handle(CancelAttendanceCommand command);

  void handle(DeleteConcertCommand command);
}

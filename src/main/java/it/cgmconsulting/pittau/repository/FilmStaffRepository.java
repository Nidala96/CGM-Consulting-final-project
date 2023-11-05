package it.cgmconsulting.pittau.repository;

import it.cgmconsulting.pittau.Entity.FilmStaff;
import it.cgmconsulting.pittau.Entity.FilmStaffId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmStaffRepository extends JpaRepository<FilmStaff, FilmStaffId> {

}

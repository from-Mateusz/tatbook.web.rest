package me.m92.tatbook_web.core.project;

import me.m92.tatbook_web.core.models.TattooProject;
import org.springframework.data.repository.CrudRepository;

public interface TattooProjectRepository extends CrudRepository<TattooProject, Long> {

}

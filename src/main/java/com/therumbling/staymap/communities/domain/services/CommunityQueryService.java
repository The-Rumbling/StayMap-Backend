package com.therumbling.staymap.communities.domain.services;
/**
 * Interfaz que define los servicios de consulta (queries) relacionados a comunidades.
 * Sigue el patrón CQRS (Command Query Responsibility Segregation), separando lógica de lectura.
 */
import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.queries.GetAllCommunitiesQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetCommunityByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CommunityQueryService {

     /**
     * Maneja la consulta para obtener todas las comunidades registradas.
     *
     * @param query Objeto de tipo GetAllCommunitiesQuery que puede contener filtros u opciones para la búsqueda.
     * @return Lista de objetos Community con todas las comunidades encontradas.
     */
    List<Community> handle(GetAllCommunitiesQuery query);
 /**
     * Maneja la consulta para obtener una comunidad específica por su ID.
     *
     * @param query Objeto de tipo GetCommunityByIdQuery que encapsula el ID de la comunidad buscada.
     * @return Optional que puede contener una instancia de Community si se encuentra, o estar vacío si no existe.
     */
    Optional<Community> handle(GetCommunityByIdQuery query);
}

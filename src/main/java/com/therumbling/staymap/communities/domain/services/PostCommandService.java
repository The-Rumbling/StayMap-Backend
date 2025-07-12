package com.therumbling.staymap.communities.domain.services;

import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.communities.domain.model.commands.CreatePostCommand;
import com.therumbling.staymap.communities.domain.model.commands.LikePostCommand;
import com.therumbling.staymap.communities.domain.model.commands.UndoLikePostCommand;
/**
 * Servicio de comandos (write‑side) para la entidad {@link Post}.
 * Forma parte de la capa de dominio bajo el patrón CQRS:
 *   • Los comandos modifican estado (crear, dar like, deshacer like).  
 *   • Los queries se encuentran en otra interfaz (read‑side).
 */
public interface PostCommandService {
     /**
     * Crea una nueva publicación en la comunidad.
     *
     * @param command Objeto de tipo {@link CreatePostCommand} que encapsula
     *                los datos necesarios (texto, imágenes, autor, comunidad, etc.).
     * @return La instancia de {@link Post} recién persistida con su ID generado.
     */
    Post handle(CreatePostCommand command);
/**
     * Registra un "Me gusta" (like) sobre una publicación existente.
     *
     * @param command Objeto {@link LikePostCommand} que contiene el ID del post
     *                y la identidad del usuario que da like.
     * @throws DomainException (personalizar) si el usuario ya dio like o el post no existe.
     */
    void handle(LikePostCommand command);
     /**
     * Revierte un "Me gusta" previamente aplicado.
     *
     * @param command Objeto {@link UndoLikePostCommand} con el ID del post
     *                y la identidad del usuario para quitar el like.
     * @throws DomainException si el usuario no había dado like o el post no existe.
     */
    
    void handle(UndoLikePostCommand command);
}

package com.peecko.admin.service;

import com.peecko.admin.domain.Playlist;
import com.peecko.admin.repository.PlaylistRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.Playlist}.
 */
@Service
@Transactional
public class PlaylistService {

    private final Logger log = LoggerFactory.getLogger(PlaylistService.class);

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    /**
     * Save a playlist.
     *
     * @param playlist the entity to save.
     * @return the persisted entity.
     */
    public Playlist save(Playlist playlist) {
        log.debug("Request to save Playlist : {}", playlist);
        return playlistRepository.save(playlist);
    }

    /**
     * Update a playlist.
     *
     * @param playlist the entity to save.
     * @return the persisted entity.
     */
    public Playlist update(Playlist playlist) {
        log.debug("Request to update Playlist : {}", playlist);
        return playlistRepository.save(playlist);
    }

    /**
     * Partially update a playlist.
     *
     * @param playlist the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Playlist> partialUpdate(Playlist playlist) {
        log.debug("Request to partially update Playlist : {}", playlist);

        return playlistRepository
            .findById(playlist.getId())
            .map(existingPlaylist -> {
                if (playlist.getName() != null) {
                    existingPlaylist.setName(playlist.getName());
                }
                if (playlist.getCounter() != null) {
                    existingPlaylist.setCounter(playlist.getCounter());
                }
                if (playlist.getCreated() != null) {
                    existingPlaylist.setCreated(playlist.getCreated());
                }
                if (playlist.getUpdated() != null) {
                    existingPlaylist.setUpdated(playlist.getUpdated());
                }

                return existingPlaylist;
            })
            .map(playlistRepository::save);
    }

    /**
     * Get all the playlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Playlist> findAll(Pageable pageable) {
        log.debug("Request to get all Playlists");
        return playlistRepository.findAll(pageable);
    }

    /**
     * Get one playlist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Playlist> findOne(Long id) {
        log.debug("Request to get Playlist : {}", id);
        return playlistRepository.findById(id);
    }

    /**
     * Delete the playlist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Playlist : {}", id);
        playlistRepository.deleteById(id);
    }
}

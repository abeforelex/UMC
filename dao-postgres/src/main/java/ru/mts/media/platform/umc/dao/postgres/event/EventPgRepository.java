package ru.mts.media.platform.umc.dao.postgres.event;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface EventPgRepository extends JpaRepository<EventPgEntity, Long> {
    @Query("SELECT e FROM EventPgEntity e WHERE e.venueReferenceId IN :venueIds ORDER BY e.venueReferenceId, e.startTime DESC")
    List<EventPgEntity> findRecentEventsByVenueIds(@Param("venueIds") List<String> venueIds);
}

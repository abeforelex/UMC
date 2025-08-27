package ru.mts.media.platform.umc.dao.postgres.venue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.media.platform.umc.dao.postgres.common.FullExternalIdPk;

import java.util.List;

@Repository
interface VenuePgRepository extends JpaRepository<VenuePgEntity, FullExternalIdPk> {

    VenuePgEntity findByReferenceId(String referenceId);

    List<VenuePgEntity> findAllByReferenceIdIn(List<String> referenceIds);
}

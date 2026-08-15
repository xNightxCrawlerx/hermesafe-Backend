package com.hermesafe.domain.repository;

import com.hermesafe.domain.entity.Package;
import com.hermesafe.domain.valueobject.PackageId;
import java.util.Optional;

public interface PackageRepository {
    void save(Package pkg);
    Optional<Package> findById(PackageId id);
}

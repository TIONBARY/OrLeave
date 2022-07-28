package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.MeetingSetting;

@Repository
public interface MeetingSettingRepository extends JpaRepository<MeetingSetting, Long> {

}

package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orleave.entity.MeetingSetting;

public interface MeetingSettingRepository extends JpaRepository<MeetingSetting, Long> {

}

package com.orleave.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orleave.dto.BanListDto;
import com.orleave.entity.Ban;
import com.orleave.entity.User;
import com.orleave.exception.BanNotFoundException;
import com.orleave.repository.BanRepository;
import com.orleave.repository.UserRepository;

@Service
public class BanServiceImpl implements BanService {

	@Autowired
	BanRepository banRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public void createBan(User user, int user2No) throws Exception {
		if (user.getNo() == user2No) throw new IllegalArgumentException();
		Optional<Ban> banCheck = banRepository.findByUserNoAndBannedNo(user.getNo(), user2No);
		if (banCheck.isPresent()) throw new IllegalArgumentException();
		Ban ban = Ban.builder()
				.user(user)
				.bannedNo(user2No)
				.build();
		banRepository.save(ban);
	}

	@Override
	@Transactional
	public List<BanListDto> findByUserNo(int userNo) throws Exception {
		List<Ban> bans = banRepository.findByUserNo(userNo);
		List<BanListDto> banDtos = new ArrayList<>();
		
		for (Ban ban : bans) {
			banDtos.add(BanListDto.builder()
					.no(ban.getNo())
					.bannedNo(ban.getBannedNo())
					.bannedUserNickname(userRepository.findById(ban.getBannedNo()).get().getNickname())
					.build());
		}
		return banDtos;
	}

	@Override
	public void deleteBan(User user, int user2No) throws Exception {
		Optional<Ban> ban = banRepository.findByUserNoAndBannedNo(user.getNo(), user2No);
		if (!ban.isPresent()) throw new BanNotFoundException();
		banRepository.delete(ban.get());
	}

}

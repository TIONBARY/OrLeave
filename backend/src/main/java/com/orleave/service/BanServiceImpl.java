package com.orleave.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orleave.dto.BanListDto;
import com.orleave.entity.Ban;
import com.orleave.entity.User;
import com.orleave.repository.BanRepository;
import com.orleave.repository.UserRepository;

@Service
public class BanServiceImpl implements BanService {

	@Autowired
	BanRepository banRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void createBan(User user, int user2No) {
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
	public List<BanListDto> findByUserNo(int userNo) {
		List<Ban> bans = banRepository.findByUserNo(userNo);
		List<BanListDto> banDtos = new ArrayList<>();
		for (Ban ban : bans) {
			banDtos.add(BanListDto.builder()
					.no(ban.getNo())
					.userNo(userNo)
					.bannedUserNickname(userRepository.findById(ban.getBannedNo()).get().getNickname())
					.build());
		}
		return banDtos;
	}

	@Override
	public void deleteBan(User user, int user2No) {
		Optional<Ban> ban = banRepository.findByUserNoAndBannedNo(user.getNo(), user2No);
		if (!ban.isPresent()) throw new IllegalArgumentException();
		banRepository.delete(ban.get());
	}

}

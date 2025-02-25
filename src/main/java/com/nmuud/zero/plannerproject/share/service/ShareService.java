package com.nmuud.zero.plannerproject.share.service;

import com.nmuud.zero.plannerproject.share.dto.ShareReplyType;
import com.nmuud.zero.plannerproject.share.entity.Share;
import com.nmuud.zero.plannerproject.share.repository.ShareRepository;
import com.nmuud.zero.plannerproject.common.AuthUser;
import com.nmuud.zero.plannerproject.common.enums.RequestStatus;
import com.nmuud.zero.plannerproject.user.entity.User;
import com.nmuud.zero.plannerproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final UserService userService;
    private final ShareRepository shareRepository;

    @Transactional
    public Share createShare(Long fromUserId, Long toUserId, Share.Direction direction) {
        final User toUser = userService.findByUserId(toUserId);
        final User fromUser = userService.findByUserId(fromUserId);

        return shareRepository.save(Share.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .direction(direction)
                .requestStatus(RequestStatus.REQUESTED)
                .build());
    }

    @Transactional
    public void replyToShareRequest(Long shareId, Long toUserId, ShareReplyType type) {
        shareRepository.findById(shareId)
                .filter(share -> share.getToUserId().equals(toUserId))
                .filter(share -> share.getRequestStatus() == RequestStatus.REQUESTED)
                .map(share -> share.reply(type))
                .orElseThrow(() -> new RuntimeException("Bad Request"));
    }


    @Transactional
    public List<Long> findSharedUserIdsByUser(AuthUser authUser) {
        return Stream.concat(
                        shareRepository.findAllByBiDirection(
                                        authUser.getId(),
                                        RequestStatus.ACCEPTED,
                                        Share.Direction.BIDIRECTIONAL
                                )
                                .stream()
                                .map(s -> s.getToUserId().equals(authUser.getId()) ? s.getFromUserId() : s.getToUserId()),
                        shareRepository.findAllByToUserIdAndRequestStatusAndDirection(authUser.getId(),
                                        RequestStatus.ACCEPTED,
                                        Share.Direction.UNIDIRECTIONAL
                                )
                                .stream()
                                .map(Share::getFromUserId)
                )
                .collect(Collectors.toList());
    }
}

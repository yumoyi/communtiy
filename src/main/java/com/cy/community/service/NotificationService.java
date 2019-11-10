package com.cy.community.service;

import com.cy.community.dto.NotificationDTO;
import com.cy.community.dto.PaginationDTO;
import com.cy.community.pojo.User;

/**
 * @author cy
 * @since 2019-11-07 16:51
 */
public interface NotificationService {
    PaginationDTO list(Long id, Integer page, Integer size);

    Long unreadCount(Long userId);

    NotificationDTO read(Long id, User user);
}


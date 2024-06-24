package ollie.wecare.user.dto;

public record MyPageResponse(String nickname,
                             Integer level,
                             boolean isAdmin,
                             String loginId) {}

package ollie.wecare.common.enums;

import lombok.Getter;
import ollie.wecare.common.base.BaseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ollie.wecare.common.base.BaseResponseStatus.WRONG_TAG_DETAIL;
import static ollie.wecare.common.base.BaseResponseStatus.WRONG_TAG_NAME;

@Getter
public enum TagEnum {
    LOCATION("위치", null),
        SEOUL("서울", LOCATION),
        GYEONGGI("경기", LOCATION),
        OTHER("그 외", LOCATION),
    CATEGORY("카테고리", null),
        SPORTS("운동", CATEGORY),
        ART("예술", CATEGORY),
        ACADEMIC("학술", CATEGORY),
        ETC("기타", CATEGORY);

    private final String tagName;
    private final TagEnum parent;
    private final List<TagEnum> child;

    TagEnum(String name, TagEnum parent) {
        this.child = new ArrayList<>();
        this.tagName = name;
        this.parent = parent;
        if(Objects.nonNull(parent)) {
            parent.child.add(this);
        }
    }

    public static TagEnum getEnumByName(String name) throws BaseException {
        return Arrays.stream(TagEnum.values())
                .filter(tagEnum -> tagEnum.getTagName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new BaseException(WRONG_TAG_NAME));
    }

    public static TagEnum getTag(TagEnum tagEnum, String tagDetail) throws BaseException {
        return Arrays.stream(TagEnum.values())
                .filter(t -> t.parent == tagEnum && t.getTagName().equalsIgnoreCase(tagDetail))
                .findFirst()
                .orElseThrow(() -> new BaseException(WRONG_TAG_DETAIL));
    }
}

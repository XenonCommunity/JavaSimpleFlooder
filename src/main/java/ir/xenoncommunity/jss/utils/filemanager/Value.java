package ir.xenoncommunity.jss.utils.filemanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Value <T>{
    private String name;
    private T value;
}

package uz.sardorbroo.jinx.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandResult implements Result {

    private boolean success;

    private String command;

    private String result;

    @Override
    public boolean getSuccess() {
        return success;
    }
}

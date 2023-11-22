package org.luoyuhan.learn.java.lombok;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {
    String username;

    public static void main(String[] args) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setId(0L);
        UserDTO userDTO1 = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setId(1L);

        System.out.println(userDTO.equals(userDTO1));
    }
}

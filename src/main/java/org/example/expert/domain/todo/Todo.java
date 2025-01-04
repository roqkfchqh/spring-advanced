package org.example.expert.domain.todo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.Timestamped;
import org.example.expert.domain.user.User;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "todos")
public class Todo extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;
    private String weather;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Todo of(String title, String contents, String weather, User user){
        Todo todo = new Todo();
        todo.title = title;
        todo.contents = contents;
        todo.weather = weather;
        todo.user = user;
        return todo;
    }
}

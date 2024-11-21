package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.model.types.ActionType;

@Entity
@Table(name = "actions")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Action {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ActionType type;

	private Integer value;

	@ManyToOne(cascade = CascadeType.ALL)
	private Sensor sensor;

	@ManyToOne(cascade = CascadeType.ALL)
	private Scenario scenario;
}

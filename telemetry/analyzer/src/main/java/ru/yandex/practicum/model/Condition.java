package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.model.types.ConditionOperation;
import ru.yandex.practicum.model.types.ConditionType;

@Entity
@Table(name = "conditions")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ConditionType type;

	@Enumerated(EnumType.STRING)
	private ConditionOperation operation;

	private Integer value;

	@ManyToOne(cascade = CascadeType.ALL)
	private Scenario scenario;

	@NotBlank
	@ManyToOne(cascade = CascadeType.ALL)
	private Sensor sensor;
}

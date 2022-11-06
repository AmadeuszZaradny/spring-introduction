package pl.umk.workshop.springintroduction.domain.models;

import java.util.List;

public record Deposit(Integer depositId, Student student, List<Item> items) {

}

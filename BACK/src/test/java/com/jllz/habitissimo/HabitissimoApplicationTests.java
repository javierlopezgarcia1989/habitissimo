package com.jllz.habitissimo;

import com.jllz.habitissimo.model.Budget;
import com.jllz.habitissimo.model.Category;
import com.jllz.habitissimo.model.Status;
import com.jllz.habitissimo.model.User;
import com.jllz.habitissimo.service.BudgetService;
import com.jllz.habitissimo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;

@SpringBootTest
class HabitissimoApplicationTests {

	@Test
	void contextLoads() {
		User newUser = createUser();
		findUser(newUser);
		Budget newBudget = createBudget();
		findBudget(newBudget);
	}

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private UserService userService;

	@Test
	private User createUser(){
		User user = new User();
		user.setAddress("Calle 9");
		user.setPhone("689789789");
		user.setEmail("pruebatest@gmail.com");

		User newUser = userService.save(user);

		Assert.notNull(newUser,"newUser is null!");

		return newUser;
	}

	@Test
	private void findUser(User newUser){
		User userSaved = userService.findUserByEmail(newUser.getEmail());

		Assert.isTrue(!newUser.equals(userSaved),"newUser is not equals to userSaved");
	}

	@Test
	private Budget createBudget(){
		Budget budget = new Budget();

		//create User
		User user = new User();
		user.setAddress("Calle 10");
		user.setPhone("689789780");
		user.setEmail("pruebatest2@gmail.com");
		budget.setUser(user);

		//create Status. Por defecto
		Status status = new Status();
		budget.setStatus(status);

		//create Category
		Category category = new Category();
		category.setName("Calefaccion");
		budget.setCategory(category);


		budget.setTitle("Budget 1");
		budget.setDescription("Budget description 1");

		Budget newBudget = budgetService.save(budget);

		Assert.notNull(newBudget,"newUser is null!");

		return newBudget;
	}

	@Test
	private void findBudget(Budget newBudget){
		Optional<Budget> budgetSaved = budgetService.findById(newBudget.getId());

		Assert.isTrue(!newBudget.equals(budgetSaved),"newBudget is not equals to budgetSaved");
	}
}

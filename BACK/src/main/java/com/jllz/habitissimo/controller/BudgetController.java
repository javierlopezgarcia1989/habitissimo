package com.jllz.habitissimo.controller;

import com.jllz.habitissimo.config.ServiceException;
import com.jllz.habitissimo.model.Budget;
import com.jllz.habitissimo.model.Status;
import com.jllz.habitissimo.model.User;
import com.jllz.habitissimo.service.BudgetService;
import com.jllz.habitissimo.service.DescriptionService;
import com.jllz.habitissimo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;
    @Autowired
    private UserService userService;
    @Autowired
    private DescriptionService descriptioService;

    /**
     * name: getBudgets
     *
     * El servicio devolverá un listado paginado de las solicitud de presupuesto. Si se le indica un
     * email como parámetro deberá devolver solo las solicitudes de presupuesto que se
     * correspondan con el usuario con ese email.
     *
     * @param email: Optional.
     * @return ResponseEntity
     */
    @GetMapping(value = "/getBudgetsEmail/{email}")
    public ResponseEntity<List<Budget>> getBudgetsEmail(@PathVariable(value = "email") String email) throws ServiceException {

            List<Budget> budgets = new ArrayList<>();

            if(null == email) {
                budgetService.findAll().forEach(budgets::add);
            }else{

                budgetService.findAll().stream()
                    .filter(e -> e.getUser().getEmail().equals(email))
                        .forEach(budgets::add);

            }
            if (budgets.isEmpty()) {
                throw new ServiceException(HttpStatus.NOT_FOUND.value(), "No hay presupuestos para el email solicitado", 1);
            }
            return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    /**
     * name: getBudgets
     *
     * El servicio devolverá un listado paginado de las solicitud de presupuesto. Si se le indica un
     * email como parámetro deberá devolver solo las solicitudes de presupuesto que se
     * correspondan con el usuario con ese email.
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/getBudgets")
    public ResponseEntity<List<Budget>> getBudgets() throws ServiceException {

        List<Budget> budgets = new ArrayList<>();

        budgetService.findAll().forEach(budgets::add);

        if (budgets.isEmpty()) {
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "No hay presupuestos en el sistema", 1);
        }
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    /**
     * name: createBudget
     *
     * El servicio creará una nueva solicitud de presupuesto en estado pendiente con los campos
     * indicados.
     *  -   Si no existe un usuario con el email indicado se creará un nuevo usuario.
     *  -   Si existe un usuario con el email indicado se modificará el teléfono y la dirección por
     * los indicados.
     * Para crear una nueva solicitud de presupuesto no son necesarios el título y la categoría.
     *
     * @param budget: Budget to update
     * @return
     */
    @PostMapping(value = "/createBudget", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget){

        User user = userService.findUserByEmail(budget.getUser().getEmail());
        if(null != user) {
            user.setPhone(budget.getUser().getPhone());
            user.setAddress(budget.getUser().getAddress());
            userService.save(user);
            budget.setUser(user);
        }
        budget.setStatus(new Status());
        Budget newBudget = budgetService.create(budget);
        return new ResponseEntity<>(newBudget, HttpStatus.CREATED);

    }

    /**
     * name: updateBudget
     *
     * Mientras la solicitud de presupuesto esté pendiente debe permitirse modificar el título, la
     * descripción y la categoría. Si la solicitud no está pendiente y se intenta modificar se deberá
     * avisar del error.
     *
     * @param id: Request id
     * @param budget: Budget to update. Títle(optional) - Description(optional) - Category(Optional)
     *
     * @return ResponseEntity
     */
    @PutMapping(value = "/updateBudget/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> updateBudget(@PathVariable("id") Long id, @RequestBody Budget budget) throws ServiceException{

        Optional<Budget> budgetData = budgetService.findById(id);
        if (budgetData.isPresent()) {
            if(budgetData.get().getStatus().getName().equals("Pendiente")) {
                Budget budgetUpdated = budgetData.get();
                if (null != budget.getTitle())
                    budgetUpdated.setTitle(budget.getTitle());
                if (null != budget.getDescription())
                    budgetUpdated.setDescription(budget.getDescription());
                if (null != budget.getCategory())
                    budgetUpdated.setCategory(budget.getCategory());
                budgetService.update(budgetUpdated);
                return new ResponseEntity<>(budgetUpdated, HttpStatus.OK);
            }else {
                throw new ServiceException(HttpStatus.NOT_FOUND.value(), "La solicitud que intentas modificar no está en estado PENDIENTE", 1);
            }
        } else {
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "La solicitud que intentas modificar no existe", 1);
        }

    }

    /**
     * name: postBudget
     *
     * Para poder publicar la solicitud de presupuesto se requiere que la solicitud esté pendiente y
     * contenga título y categoría. El servicio comprobará que la solicitud cumpla los requisitos y, si
     * los cumple, modificará el estado a publicada. Si el presupuesto no cumple con los requisitos se
     * deberá avisar del error.
     *
     * @param id: Request id
     *
     * @return ResponseEntity
     */
    @PutMapping("/postBudget/{id}")
    public ResponseEntity<Budget> postBudget(@PathVariable("id") Long id) throws ServiceException{
        Optional<Budget> budgetData = budgetService.findById(id);
        Budget budget = budgetData.get();
        if (budgetData.isPresent() && budget.getStatus().getName().equals("Pendiente") && null != budget.getCategory() && null != budget.getTitle()) {
            Status posted = new Status();
            posted.setName("Publicada");
            budget.setStatus(posted);
            budgetService.save(budget);
            return new ResponseEntity<>(budget, HttpStatus.OK);
        }else{
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "El presupuesto no cumple los requisitos para ser publicado", 1);
        }
    }

    /**
     * name: discardBudget
     *
     * Este servicio modifica el estado de una solicitud pendiente o publicada y lo pone como
     * descartada. Si la solicitud ya ha sido descartada deberá avisar del error.
     *
     * @param id: Request id
     *
     * @return ResponseEntity
     */
    @PutMapping("/discardBudget/{id}")
    public ResponseEntity<Budget> discardBudget(@PathVariable("id") Long id) throws ServiceException{
        Optional<Budget> budgetData = budgetService.findById(id);
        Budget budget = budgetData.get();
        if (budgetData.isPresent() && (budget.getStatus().getName().equals("Pendiente") || budget.getStatus().getName().equals("Publicada"))) {
            Status posted = new Status();
            posted.setName("Descartada");
            budget.setStatus(posted);
            budgetService.save(budget);
            return new ResponseEntity<>(budget, HttpStatus.OK);
        }else if(budget.getStatus().getName().equals("Descartada")){
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "La solicitud ya ha sido descartada previamente", 1);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * name: getCategoryDescription
     *
     * El servicio devolverá una categoría que encaje con la descripcion del presupuesto solicitado
     *
     * @param description: description
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/getCategoryDescription/{description}")
    public ResponseEntity<List<String>> getCategoryDescription(@PathVariable("description") String description){

        try {
            List<String> category = descriptioService.getCategoryDescription(description);
            if (category.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

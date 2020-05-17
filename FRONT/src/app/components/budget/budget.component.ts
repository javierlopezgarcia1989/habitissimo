import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators,} from '@angular/forms';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { BudgetService } from '../../services/budgetservice.service';
import {MatStepperModule} from '@angular/material/stepper';

interface Category {
  name: string;
}
interface Subcategory {
  value: string;
}
interface PricePreference {
  value: string;
  viewValue: string;
}
interface DatePreference {
  value: string;
  viewValue: string;
}
const API_CREATE_BUDGET = "http://localhost:8080/api/budget/createBudget"
const API_CATEGORIES = "http://localhost:8080/api/category/getCategories"


@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.css']
})

export class BudgetComponent implements OnInit {

  stepIndex = 0;
  stepper;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  progressbarValue = 0;
  selectedCategory = false;
  resume;
  datePreference;
  category;
  subcategories: Subcategory[];
  categories: Category[];
  emailValidated = false;
  showEmailError = false;
  budget;
  categorySuggested;
  //LocalStorage
  firstFormGroupStorage;
  secondFormGroupStorage;
  thirdFormGroupStorage;
  resumeStorage;


  pricePreferences: PricePreference[] = [
    {value: 'Lo más barato', viewValue: 'Lo más barato'},
    {value: 'Relación calidad precio', viewValue: 'Relación calidad precio'},
    {value: 'Mejor calidad', viewValue: 'Mejor calidad'},
  ];
  datePreferences: DatePreference[] = [
    {value: 'lo-antes-posible-0', viewValue: 'Lo antes posible'},
    {value: 'de-1-a-3-meses-1', viewValue: 'De 1 a 3 meses'},
    {value: 'Mas-de-3-meses-2', viewValue: 'Más de 3 meses'},
  ];

  constructor(private _formBuilder: FormBuilder, public http: HttpClient, private budgetservice: BudgetService) {}

  ngOnInit() {
    this.getLocalStorage();
    this.validateForms(this.firstFormGroup, this.secondFormGroup, this.thirdFormGroup); 
    this.getCategories();
  }

  getLocalStorage(){
    this.stepIndex = JSON.parse(localStorage.getItem('stepIndex'));
    if(localStorage.getItem('firstForm')){
        this.firstFormGroupStorage = JSON.parse(localStorage.getItem('firstForm'));
        this.getCategoryDescription(this.firstFormGroupStorage.description);
      }
    if(localStorage.getItem('secondForm')){
      this.secondFormGroupStorage = JSON.parse(localStorage.getItem('secondForm'))
      this.updateSubcategories(this.secondFormGroupStorage.category);
    }
    if(localStorage.getItem('thirdForm')){
      this.thirdFormGroupStorage = JSON.parse(localStorage.getItem('thirdForm'))
      this.checkEmail(this.thirdFormGroupStorage.email);
    }
    this.resumeStorage = JSON.parse(localStorage.getItem('resumeStorage'));
    this.resume = this.resumeStorage || null;
  }

  validateForms(form1, form2, form3){
    this.firstFormGroup = this._formBuilder.group({
      description: [this.firstFormGroupStorage && this.firstFormGroupStorage.description || '', Validators.required],
      title: [this.firstFormGroupStorage && this.firstFormGroupStorage.title || ''],
      datePreference: [this.firstFormGroupStorage && this.firstFormGroupStorage.datePreference || ''],
    });
    this.secondFormGroup = this._formBuilder.group({
      category: [this.secondFormGroupStorage && this.secondFormGroupStorage.category || '',Validators.required],
      subcategory: [this.secondFormGroupStorage && this.secondFormGroupStorage.subcategory || '',Validators.required],
      pricePreference: [this.secondFormGroupStorage && this.secondFormGroupStorage.pricePreference || '',Validators.required]
    });
    this.thirdFormGroup = this._formBuilder.group({
      name: [this.thirdFormGroupStorage && this.thirdFormGroupStorage.name || '', Validators.required],
      address: [this.thirdFormGroupStorage && this.thirdFormGroupStorage.address || ''],
      email: [this.thirdFormGroupStorage && this.thirdFormGroupStorage.email || '', Validators.compose([ Validators.required, Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')])],
      phone: [this.thirdFormGroupStorage && this.thirdFormGroupStorage.phone || '', Validators.pattern('[6789][0-9]{8}')]
    });
  }

  updatePercentage(step, stepper){
    this.stepIndex = step;
    localStorage.setItem('stepIndex', JSON.stringify(step));
    if(step == 0){
      this.progressbarValue = 0;
    }else if(step == 1){
      this.progressbarValue = 33;
      localStorage.setItem('firstForm', JSON.stringify(this.firstFormGroup.value));
    }else if(step == 2){
      this.progressbarValue = 66;
      localStorage.setItem('secondForm', JSON.stringify(this.secondFormGroup.value));
    }else if(step == 3){
      this.progressbarValue = 100;
      localStorage.setItem('thirdForm', JSON.stringify(this.thirdFormGroup.value));
      this.showResume(this.firstFormGroup.value, this.secondFormGroup.value, this.thirdFormGroup.value);
    }
  }

  showResume(firstForm, secondForm, thirdForm){
    this.resume = {
      firstForm,
      secondForm,
      thirdForm
    }
    localStorage.setItem('resumeStorage', JSON.stringify(this.resume));
  }

  updateSubcategories(category){
    this.selectedCategory = true;
    this.getSubcategories(category);
  }

  getSubcategories(category){
    this.budgetservice.getSubcategories(category).subscribe(
      result => {
        this.subcategories = this.parsesubcategories(result);
      },
      error => {
          console.log(<any>error);
      }
  );
  }

  getCategories(){
    this.budgetservice.getCall(API_CATEGORIES).subscribe(
      resp => {
        this.categories = Object.values(resp);
      },
      error => {
        alert("Ha habido un error en el envío del presupuesto")
      }
    )
  }

  parsesubcategories(result){
    result = result.map(element => element.name);
    return result;
  }

  checkEmail(email){
     this.budgetservice.checkEmail(email).subscribe(
      result => {
        this.emailValidated = true;
        this.showEmailError = false ;
      },
      error => {
        this.emailValidated = false;
        this.showEmailError = true;
      }
    );
  }

  getCategoryDescription(description){
    this.budgetservice.getCategoryFromDescription(description).subscribe(
     result => {
       this.categorySuggested = result;
     },
     error => {
       console.log("Error al sugerir categoria: ", error);
    }
   );
 }

  buildBudgetForm(){
    this.budget = {
      ...this.resume,
      datePreference: this.resume.firstForm.datePreference,
      description: this.resume.firstForm.description,
      title: this.resume.firstForm.title,
      user: {
        address: this.resume.thirdForm.address,
        email: this.resume.thirdForm.email,
        name: this.resume.thirdForm.name,
        phone: this.resume.thirdForm.phone
      },
      category: {
        name: this.resume.secondForm.category,
        subcategories: [
          {
            name: this.resume.secondForm.subcategory
          }
        ]
      }
    }
    delete this.budget.firstForm;
    delete this.budget.secondForm;
    delete this.budget.thirdForm;
  }
  createBudget(){
    this.buildBudgetForm();
    this.budgetservice.postCall(API_CREATE_BUDGET, this.budget).subscribe(
      resp => {
        alert("Presupuesto enviado con éxito");
      },
      error => {
        alert("Ha habido un error en el envío del presupuesto")
      }
    )
  }
  reset(stepper){
    stepper.reset();
    localStorage.clear();
  }
}

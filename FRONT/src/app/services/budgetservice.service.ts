import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
 
const API_SUBCATEGORIES = "http://localhost:8080/api/subcategory/getSubcategories/"
const API_CATEGORY_DESCRIPTION = "http://localhost:8080/api/budget/getCategoryDescription/"
const API_CHECK_EMAIL = "http://localhost:8080/api/user/checkEmailUser/"

@Injectable()
export class BudgetService{

    constructor( public http: HttpClient){}
 
    getSubcategories(category): Observable<any>{
        return this.http.get(API_SUBCATEGORIES + category);
    }
    getCategoryFromDescription(description): Observable<any>{
        return this.http.get(API_CATEGORY_DESCRIPTION + description);
    }
    checkEmail(email): Observable<any>{
        return this.http.get(API_CHECK_EMAIL + email);
    }
    postCall(URL: string, dto:Object): Observable<HttpResponse<Object>> {
        return this.http.post<HttpResponse<Object>>(URL,dto, {});
    }
    getCall(URL: string): Observable<HttpResponse<Object>> {
        return this.http.get<HttpResponse<Object>>(URL);
    }

}
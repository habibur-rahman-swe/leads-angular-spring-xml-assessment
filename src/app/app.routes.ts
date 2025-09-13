import { provideRouter, Routes } from '@angular/router';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { CreateEmployeeComponent } from './create-employee/create-employee.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app.component';

export const routes: Routes = [
  { path: '', redirectTo: 'employees', pathMatch: 'full' },
  { path: 'employees', component: EmployeeListComponent },
  { path: 'employees/create', component: CreateEmployeeComponent },
  { path: 'employees/edit/:employeeId', component: UpdateEmployeeComponent },
];

export const APP_ROUTES = provideRouter(routes);

bootstrapApplication(AppComponent, {
  providers: [APP_ROUTES]
});

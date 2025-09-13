import { Component, OnInit } from '@angular/core';
import { Employee } from '../models/employee.model';
import { EmployeeService } from '../services/employee.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employee-list',
  imports: [CommonModule, RouterModule],
  providers: [EmployeeService],
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css'
})
export class EmployeeListComponent implements OnInit {

  employees: Employee[] = [];
  selectedFile: File | null = null;

  constructor(private employeeService: EmployeeService, private router: Router) { }

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees() {
    this.employeeService.getEmployees().subscribe((data: Employee[]) => {
      this.employees = data;
      // console.log(this.employees);
    });
  }

  deleteEmployee(id: any) {
    this.employeeService.deleteEmployee(id).subscribe(() => {
      this.getEmployees();
    });
  }

  editEmployee(id: any) {
    this.router.navigate(['./employees/edit/', id]);
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  importEmployees() {
    if (this.selectedFile) {
      this.employeeService.importEmployees(this.selectedFile).subscribe(
        (data: Employee[]) => {
          alert('Employees imported successfully!');
          this.getEmployees(); // Refresh the employee list
        },
        (error) => {
          console.error('Error importing employees:', error);
          alert('Failed to import employees. Please check the file format.');
        }
      );
    } else {
      alert('Please select a file to import.');
    }
  }
}

import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({ providedIn: 'root' })
export class UnauthGuard implements CanActivate {

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  public canActivate(): Observable<boolean> {
    return this.authService.isAuthenticated$.pipe(
      map(isAuthenticated => {
        if (isAuthenticated) {
          this.router.navigate(['/articles']);
          return false;
        }
        return true;
        //return isAuthenticated;
      })
    );
  }
}

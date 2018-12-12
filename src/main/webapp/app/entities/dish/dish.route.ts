import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dish } from 'app/shared/model/dish.model';
import { DishService } from './dish.service';
import { DishComponent } from './dish.component';
import { DishDetailComponent } from './dish-detail.component';
import { DishUpdateComponent } from './dish-update.component';
import { DishDeletePopupComponent } from './dish-delete-dialog.component';
import { IDish } from 'app/shared/model/dish.model';

@Injectable({ providedIn: 'root' })
export class DishResolve implements Resolve<IDish> {
    constructor(private service: DishService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Dish> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Dish>) => response.ok),
                map((dish: HttpResponse<Dish>) => dish.body)
            );
        }
        return of(new Dish());
    }
}

export const dishRoute: Routes = [
    {
        path: 'dish',
        component: DishComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Dishes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dish/:id/view',
        component: DishDetailComponent,
        resolve: {
            dish: DishResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dishes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dish/new',
        component: DishUpdateComponent,
        resolve: {
            dish: DishResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dishes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dish/:id/edit',
        component: DishUpdateComponent,
        resolve: {
            dish: DishResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dishes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dishPopupRoute: Routes = [
    {
        path: 'dish/:id/delete',
        component: DishDeletePopupComponent,
        resolve: {
            dish: DishResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dishes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

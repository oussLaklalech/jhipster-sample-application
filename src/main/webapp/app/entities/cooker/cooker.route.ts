import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cooker } from 'app/shared/model/cooker.model';
import { CookerService } from './cooker.service';
import { CookerComponent } from './cooker.component';
import { CookerDetailComponent } from './cooker-detail.component';
import { CookerUpdateComponent } from './cooker-update.component';
import { CookerDeletePopupComponent } from './cooker-delete-dialog.component';
import { ICooker } from 'app/shared/model/cooker.model';

@Injectable({ providedIn: 'root' })
export class CookerResolve implements Resolve<ICooker> {
    constructor(private service: CookerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Cooker> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Cooker>) => response.ok),
                map((cooker: HttpResponse<Cooker>) => cooker.body)
            );
        }
        return of(new Cooker());
    }
}

export const cookerRoute: Routes = [
    {
        path: 'cooker',
        component: CookerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cookers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cooker/:id/view',
        component: CookerDetailComponent,
        resolve: {
            cooker: CookerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cookers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cooker/new',
        component: CookerUpdateComponent,
        resolve: {
            cooker: CookerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cookers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cooker/:id/edit',
        component: CookerUpdateComponent,
        resolve: {
            cooker: CookerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cookers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cookerPopupRoute: Routes = [
    {
        path: 'cooker/:id/delete',
        component: CookerDeletePopupComponent,
        resolve: {
            cooker: CookerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cookers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

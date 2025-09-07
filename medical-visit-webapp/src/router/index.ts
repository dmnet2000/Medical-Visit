import {createRouter, createWebHistory} from 'vue-router';
import Home from '../component/view/Home.vue';
import Login from '@/component/view/Login.vue';
import NotFound from '@/component/view/NotFound.vue';
import Import from '@/component/search/Import.vue';
import SearchForm from '@/component/search/SearchForm.vue';

const routes = [
    { path: '/home', component: Home , children: [{ path: 'import', component: Import },
                                                    {path: 'search', component: SearchForm }]},
    { path:'/login', component: Login },
    { path: '/', component: Login },
    
    //{ path: '/search', component: Search },
    { path: '/:pathMatch(.*)', name: 'bad-not-found', component: NotFound },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
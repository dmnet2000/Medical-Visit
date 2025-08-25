import {defineStore} from 'pinia'

export const useAuthStore = defineStore ('auth', {
    state: () => ({
        isAuthenticated: false,
        user: null as string | null,
        jwt:null as string| null,
    }),
    actions:{
        login(user: string, jwt:string){
            this.isAuthenticated = true
            this.user = user 
            this.jwt = jwt 

        },
        logout(){
            this.isAuthenticated = false
            this.user = null
            this.jwt = null
        }
    }
})
package assembleur.visual;

import assembleur.visual.ExitTrappedException;

import java.security.Permission;
public class ExitTrapper {
    public static void forbidSystemExitCall() {
        final SecurityManager securityManager = new SecurityManager() {
            public void checkPermission( Permission permission ) {
                if( permission.getName().startsWith("exitVM") ) {
                    int code = Integer.parseInt(permission.getName().split("\\.")[1]);
                    throw new ExitTrappedException(code) ;
                }
            }
        } ;
        System.setSecurityManager( securityManager ) ;
    }
    public static void enableSystemExitCall() {
        System.setSecurityManager( null ) ;
    }
}


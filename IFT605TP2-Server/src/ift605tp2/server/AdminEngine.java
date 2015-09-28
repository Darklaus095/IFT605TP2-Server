/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift605tp2.server;

import contracts.IAdminHandler;
import ift605tp2.server.contracts.ITaskStorage;
import java.rmi.RemoteException;
import java.util.Random;

/**
 *
 * @author Michaël
 */
public class AdminEngine extends DerivationEngine implements IAdminHandler {
    private long m_adminKey = -1;

    public AdminEngine(ITaskStorage storage) throws RemoteException {
        super(storage);
    
        Random rand = new Random();
        
        do {
            m_adminKey = rand.nextLong();
        } while(m_adminKey == -1);
    }

    @Override
    public long Connect(String username, String password) throws RemoteException {
        return m_adminKey;
    }

    @Override
    public boolean StopTask(String name, long adminKey) throws RemoteException {
        if (adminKey != m_adminKey)
            return false;
        
        Thread t = m_storage.GetTask(name);
        t.interrupt();
        return m_storage.RemoveTask(name);
    }

    @Override
    public String[] GetCurrentlyRunningTask(long adminKey) throws RemoteException {
        if (adminKey != m_adminKey)
            return null;
        
        return m_storage.GetCurrentTasks();
    }
}

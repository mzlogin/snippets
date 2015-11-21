/**
 * @file AutoLock.h 
 * @author ChumpMa@gmail.com
 * @brief A scope autolock util
 * @version 0.0.0.1
 * @date 2014-6-8
 */

/**
 * Usage:
 * 
 * using MzLib::CAutoLock;
 * using MzLib::CSyncObj;
 * 
 * class CDemo
 * {
 * ...
 * public:
 *     void SyncFunc()
 *     {
 *         CAutoLock lock(&m_syncObj);
 *         ...
 *     }
 * private:
 *     CSyncObj m_syncObj;
 * ...
 * };
 */

#ifndef _AUTO_LOCK_H_
#define _AUTO_LOCK_H_

#include <Windows.h>

namespace MzLib
{
    /**
     * Synchronize object implemented via Win32 Event Object
     */
    class CSyncObj
    {
    public:
        CSyncObj()
        {
            m_hSync = ::CreateEvent(NULL, FALSE, TRUE, NULL);
        }

        ~CSyncObj()
        {
            if (m_hSync)
            {
                ::CloseHandle(m_hSync);
            }
        }

        void Lock(DWORD dwMiliSeconds = INFINITE)
        {
            if (m_hSync)
            {
                ::WaitForSingleObject(m_hSync, dwMiliSeconds);
            }
        }

        void Unlock()
        {
            if (m_hSync)
            {
                ::SetEvent(m_hSync);
            }
        }

    private:
        HANDLE m_hSync;
    };

    /** 
     * Autolock implemented via Constructor and Destructor
     */
    class CAutoLock
    {
    public:
        CAutoLock(CSyncObj *psyncObj) : m_psyncObj(psyncObj)
        {
            if (m_psyncObj)
            {
                m_psyncObj->Lock();
            }
        }

        ~CAutoLock()
        {
            if (m_psyncObj)
            {
                m_psyncObj->Unlock();
            }
        }

    private:
        CSyncObj *m_psyncObj;
    };

}   // namespace MzLib

#endif // _AUTO_LOCK_H_

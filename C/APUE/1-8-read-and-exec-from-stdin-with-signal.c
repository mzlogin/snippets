#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

#define MAXLINE 4096

void sig_int(int signo) {
    printf("interrupt\n%% ");
}

int main(int argc, char *argv[]) {
    char buf[MAXLINE];
    pid_t pid;
    int status;

    if (signal(SIGINT, sig_int) == SIG_ERR) {
        printf("signal error");
        return -1;
    }

    printf("%% ");
    while (fgets(buf, MAXLINE, stdin) != NULL) {
        buf[strlen(buf) - 1] = 0;

        if ((pid = fork()) < 0) {
            printf("fork error");
        } else if (pid == 0) { /* child */
            execlp(buf, buf, (char*)0);
            printf("couldn't execute: %s\n", buf);
            return -1;
        }

        if ((pid = waitpid(pid, &status, 0)) < 0) {
            printf("waitpid error");
        }

        printf("%% ");
    }

    return 0;
}

#include <jni.h>
#include <stdio.h>
#include <fcntl.h>
#include <ctype.h>
#include <fcntl.h> //文件控制
#include <linux/ioctl.h>
#include <sys/ioctl.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <android/log.h>
#include <math.h>

#define  PowerOn     0x01
#define  PowerOff    0x02
#define  XfrAPDU     0x03

#define SLOT_ICC     0
#define SLOT_SAM1    1
#define SLOT_SAM2    2
#define SLOT_SAM3    3

#define PCD_CMD(n, m) ((n << 4) | m)


extern "C" {

unsigned char RecBuf[271];
unsigned char CmdBuf[271];
int fd;

typedef struct {
    unsigned char *p_iBuf;
    unsigned char *p_oBuf;
    unsigned int iDataLen;
    unsigned int oDataLen;
    unsigned int statusCode;
} IFD_PARAM;


IFD_PARAM UsrParam;
unsigned char StrToHex(char *src, int len) {
    int i = 0;
    unsigned char a = 0;
    //__android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "start str to hex ...");
    if (src == NULL) return 0;
    else {
        while (len--) {
            //        PrtMsg("src + %d = 0x%X\n", i, *(src+i));
            // __android_log_print(ANDROID_LOG_VERBOSE, "Pn512","src + %d = %c\n", i, *(src+i));
            usleep(1000);
            if (*(src + i) >= '0' && *(src + i) <= '9')
                a = a * 16 + (src[i] - '0');
            else if (*(src + i) >= 'a' && *(src + i) <= 'f')
                a = a * 16 + (src[i] - 'a') + 10;
            else if (*(src + i) >= 'A' && *(src + i) <= 'F')
                a = a * 16 + (src[i] - 'A') + 10;
            else if (*src + i == 'x' || *src + i == 'X');

            ++i;
        }
        // __android_log_print(ANDROID_LOG_VERBOSE, "Pn512","a=0x%x", a);
        return a;
    }
}
char HexToChar(char temp) {
    char dst;
    if (temp < 10) {
        dst = temp + '0';
    } else {
        dst = temp - 10 + 'A';
    }/*else if(temp>='a'&&temp<='f'){
        dst = temp -10 +'a';
    }else if(temp>='A'&&temp<='F'){
        dst = temp -10 +'A';
    }*/
    return dst;
}
int CardPowerOn() {
    unsigned char retry;
    long retval;
    int i;
    retry = 0;
    do {
        ioctl(fd, 0x20, &UsrParam);
        usleep(50000);
        UsrParam.oDataLen = 32;
        if ((retval = ioctl(fd, 0x10, &UsrParam)) >= 0) {
            break;
        }
        retry++;
    } while (retry < 3);

    if (retry >= 3) {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "Operation fail with Errorcode = %lX",
                            retval);
        return -1;
    } else {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "ATR Len: %d",
                            UsrParam.oDataLen);
        //  for (i = 0; i < UsrParam.oDataLen; i++) {
        //     __android_log_print(ANDROID_LOG_VERBOSE, "Pn512","RecBuf[%d]= 0x%02X",i, RecBuf[i]);
        // }
        return 0;
    }
}

JNIEXPORT jint
JNICALL
Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_Pn512Card_Pn512_1OpenAndPowerOn(JNIEnv *env, jobject instance) {

    // TODO
    long retval;
    fd = open("/dev/pcd", O_RDWR);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "open /dev/pcd Pn512 device Error..");
        return 0;
    } else {
        //CardPowerOn();
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "open /dev/pcd Pn512 device sucess");
        return 1;
    }
}

JNIEXPORT jint
JNICALL
Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_Pn512Card_Pn512_1PowerOffAndClose(JNIEnv *env, jobject instance) {

    // TODO
    long retval;
    if (retval = ioctl(fd, 0x20, &UsrParam) < 0) {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "close /dev/pcd Pn512 device Error..");
        return 0;
    } else {
        if (fd > 0) {
            close(fd);
        }
        return 1;
    }

}
JNIEXPORT jboolean
JNICALL
Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_Pn512Card_CardDetect(JNIEnv *env, jobject instance) {

    // TODO
    fd_set rfds;
    fd_set wfds;
    int card_val;
    int ret = 0;
    FD_ZERO(&rfds);
    FD_SET(fd, &rfds);
    __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "Insert Mifare Please!");
    select(fd + 1, &rfds, NULL, NULL, NULL);
    __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "FT select");
    if (FD_ISSET(fd, &rfds)) {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "FD_ISSET");
        ret = read(fd, &card_val, 1);
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "Mifare Insert OK! ");
        printf("Mifare Insert OK! \n");
        if (ret < 0) {
            __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "Read Error!\n");
            return false;
        }
        return true;
    }
    __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "CardDetect END");
}
JNIEXPORT jstring
JNICALL
Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_Pn512Card_Pn512_1CardCmdOperation(JNIEnv *env, jobject instance,
                                                           jstring cmdstr) {
    int retval;
    int i = 0, j = 0, k = 0, len = 0;

    char *toJava;
    char *cmd = (char *) env->GetStringUTFChars(cmdstr, 0);
    //__android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "cmd=%s",cmd);
    UsrParam.p_oBuf = RecBuf;
    UsrParam.p_iBuf = CmdBuf;
    // TODO
    CardPowerOn();
    // __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "power on pn512 sucess and start operation");
    char *pBuf;
    pBuf = (char *) cmd;
    len = strlen(pBuf);
    // __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", " start operation pBuf");
    // __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", " pBuf len=%d,pBuf=%s",len,pBuf);

    while (*(pBuf + j) != '\0' && j <= len) {
        CmdBuf[i] = StrToHex(pBuf + j, 2);
        j += 2;
        i++;
    }
    __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "i = %d\t pBuf = %s\n", i, pBuf);
    UsrParam.p_iBuf = CmdBuf;
    UsrParam.iDataLen = i;
    UsrParam.oDataLen = 271;
    if ((retval = ioctl(fd, 0x30, &UsrParam)) < 0) {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "Operation fail with errorcode = %08X",
                            retval);
        return env->NewStringUTF("write pn512 fail");
    } else {
        char str[UsrParam.oDataLen * 2];
        char dst[UsrParam.oDataLen * 2 + 1];
        for (i = 0; i < UsrParam.oDataLen; i++) {
            str[2 * i] = RecBuf[i] >> 4;
            str[2 * i + 1] = RecBuf[i] & 0xf;
        }
        for (i = 0; i < UsrParam.oDataLen * 2; i++) {
            dst[i] = HexToChar(str[i]);
            __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", " dst[%d] = %c\n", i, dst[i]);
        }
        dst[UsrParam.oDataLen * 2] = '\0';
        toJava = dst;
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", " toJava = %s\n", toJava);
        env->ReleaseStringUTFChars(cmdstr, cmd);
        return env->NewStringUTF(toJava);
    }

}

//====== lock part ======

int fd_l = 0;
/*
 * Class:     com_dchip_hd_led_gpio_LedCtrl
 * Method:    open
 * Signature: ()Z
 */
JNIEXPORT jboolean
JNICALL Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_Pn512Lock_open
        (JNIEnv *env, jobject obj) {
    fd_l = open("/dev/door_gpio", O_RDWR);
    if (fd_l < 0) {
    __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "open /dev/door_gpio Error..");
        return false;
} else {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "open /dev/door_gpio  fd = %d", fd_l);
        return true;
    }
}

/*
 * Class:     com_dchip_hd_led_gpio_LedCtrl
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_Pn512Lock_close
(JNIEnv * env , jobject obj ) {
if ( fd_l > 0 ) {
close(fd_l);
__android_log_print(ANDROID_LOG_VERBOSE, "Pn512" , "led_gpio device close" ) ;
}
}

/*
 * Class:     com_dchip_hd_led_gpio_LedCtrl
 * Method:    ioctl
 *cmd为0x00,0x01和0x02:当为0x00时，设置IO电平为高1，当为0x01时设置IO电平为低0,0x03对应的为获取IO电平
 *arg对应的为管脚IO：0为LOCK_CTRL,1为DOOR1_ST,2为DOOR2_ST，3为LOCK_JUDGE,4为FB1_ELSE,
 *管脚LOCK_CTRL和FB1_ELSE可以设置高低电平，其他不可以。但是所有管脚都可以获取高低电平。
 * Signature: (II)I
 */
JNIEXPORT jint
JNICALL Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_Pn512Lock_ioctl
        (JNIEnv * env, jobject obj,jint cmd, jint arg){
    __android_log_print(ANDROID_LOG_VERBOSE,"Pn512","in ioctl fd_l=%d cmd=%d arg=%d",fd_l,cmd,arg);
    int ret;

    ret = ioctl(fd_l, cmd, arg);
    if(ret<0)
        __android_log_print(ANDROID_LOG_VERBOSE,"Pn512","ioctl the %d gpio fail",arg);
    else{
//        if (cmd==3) __android_log_print(ANDROID_LOG_VERBOSE,"Pn512","ioctl the %d gpio get %d",arg,ret);
//        else __android_log_print(ANDROID_LOG_VERBOSE,"Pn512","ioctl the %d gpio to %d",arg,cmd);
    }
    if (ret>0) return 0;
    else return 1;
}


int fd_hc = 0;

JNIEXPORT jboolean
JNICALL Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_HumanCheck_open
        (JNIEnv * env, jobject obj){
    fd_hc = open("/dev/safe_sw", O_RDWR);
    if (fd_hc < 0) {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "open /dev/safe_sw Error..");
        return false;
    } else {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "open /dev/safe_sw  fd = %d", fd_hc);
        return true;
    }
}

JNIEXPORT jint
JNICALL Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_HumanCheck_check
        (JNIEnv * env, jobject obj){
    if (fd_hc < 0) {
        __android_log_print(ANDROID_LOG_VERBOSE, "Pn512", "open /dev/safe_sw device not open Error.. ");
        return -99;
    }
    int ret = ioctl(fd_hc, 3, 0);
    return ret;

}

JNIEXPORT void
JNICALL Java_com_dchip_door_smartdoorsdk_deviceControl_nativelev_HumanCheck_close
        (JNIEnv * env, jobject obj){
    close(fd_hc);
}

}
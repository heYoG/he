/* DO NOT EDIT THIS FILE - it is machine generated */
#include "jni.h"
/* Header for class srvSeal_PdfSealUtil */

#ifndef _Included_srvSeal_PdfSealUtil
#define _Included_srvSeal_PdfSealUtil
#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_setCtrlPath(JNIEnv *, jobject, jstring);
/*
 * Class:     srvSeal_SrvSealUtil
 * Method:    addSeal
 * Signature: (Ljava/lang/String;Ljava/lang/String;III)I;
 */
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_openObj(JNIEnv *, jobject, jstring, jint, jint);

JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_addPage(JNIEnv *, jobject, jint, jstring, jstring);

JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_saveFile(JNIEnv *env, jobject obj, jint nObjID, jstring savePath, jstring saveType, jint keepObj);

JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_addSeal(JNIEnv *, jobject, jint, jstring, jstring, jstring);

JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_getPageCount(JNIEnv *, jobject, jint);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_login(JNIEnv *env, jobject obj, jint nObjID, jint nLoginType, jstring userID, jstring pwd)
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_login(JNIEnv *, jobject, jint, jint, jstring, jstring);

JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_verify(JNIEnv *, jobject, jint);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_setValue(JNIEnv *env, jobject obj, jint nObjID, jstring name, jstring value);
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_setValue(JNIEnv *, jobject, jint, jstring, jstring);

//JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getValue(JNIEnv *env, jobject obj, jint nObjID, jstring name);
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getValue(JNIEnv *, jobject, jint, jstring);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_setDocProperty(JNIEnv *env, jobject obj, jint nObjID, jstring name, jstring value);
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_setDocProperty(JNIEnv *, jobject, jint, jstring, jstring);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_insertNode(JNIEnv *env, jobject obj, jint nObjID, jstring nodeValue, jint tagType, jstring tagName);
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_insertNode(JNIEnv *, jobject, jint, jstring, jint, jstring);

//name:节点名，lZoom:获取的图片大小和显示大小的百分比，建议100；返回值为jpg图像的base64编码
//JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getImage(JNIEnv *env, jobject obj, jint nObjID, jstring name, jint lZoom);
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getImage(JNIEnv *, jobject, jint, jstring, jint);

//插入图片
//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_insertPicture(JNIEnv *env, jobject obj, jint nObjID, jstring picValue, jint nPageIndex, jint nLeft, jint nTop, jint nZoom);
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_insertPicture(JNIEnv *, jobject, jint, jstring, jint, jint, jint, jint);


//oriData:原始串，返回值为该串数字信封
//JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_encEnvelop(JNIEnv *env, jobject obj, jstring oriData, jstring certAlias)-
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_encEnvelop(JNIEnv *, jobject, jstring, jstring);

//encData:数字信封串，返回值为原值
//JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_decEnvelop(JNIEnv *env, jobject obj, jstring encData, jstring certAlias)
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_decEnvelop(JNIEnv *, jobject, jstring, jstring);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_getDocType(JNIEnv *env, jobject obj, jstring strFile)
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_getDocType(JNIEnv *, jobject, jstring);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_PrintDoc(JNIEnv *env, jobject obj, jint nObjID, jstring printName, jstring docName, jint prnCopys, jint blDuplex)
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_PrintDoc(JNIEnv *, jobject, jint, jstring, jstring, jint, jint);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_getNodeNum(JNIEnv *env, jobject obj, jint nObjID, jint nNodeType)
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_getNodeNum(JNIEnv *, jobject, jint, jint);

//JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getPrinterList(JNIEnv *env, jobject obj)
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getPrinterList(JNIEnv *, jobject);

//JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getPrinterStatusByStr(JNIEnv *env, jobject obj, jstring printName)
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getPrinterStatusByStr(JNIEnv *, jobject, jstring);

//JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getJobInfoByStr(JNIEnv *env, jobject obj, jstring printName, jint nJobID)
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_getJobInfoByStr(JNIEnv *, jobject, jstring, jint);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_resetPrinterByStr(JNIEnv *env, jobject obj, jstring printName)
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_resetPrinterByStr(JNIEnv *, jobject, jstring);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_cancelJob(JNIEnv *env, jobject obj, jstring printName, jint nJobID)
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_cancelJob(JNIEnv *env, jobject obj, jstring, jint);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_verifyData(JNIEnv *env, jobject obj, jstring pcOriData, jstring pcSignData, jstring pcCertData)
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_verifyData(JNIEnv *env, jobject obj, jstring, jstring, jstring);

//CreateSeal:pcBmpFileOrBase64(lParam2), sealFlag<|>sealID<|>sealName<|>compName(lParam3), width(32-17bit)+Height(16-1bit)(lParam4),ppcOutBuff(lParam5)

//JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_createSeal(JNIEnv *env, jobject obj, jstring cBmp, jint sealFlag, jstring sealID, jstring sealName, 
//  jstring compName, jint width, jint height)
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_createSeal(JNIEnv *env, jobject obj, jstring, jint, jstring, jstring, jstring, jint, jint);

//JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_grayPdf(JNIEnv *env, jobject obj, jstring pcInFile, jstring pcOutFile)
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_grayPdf(JNIEnv *env, jobject obj, jstring, jstring);

JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_protectDoc(JNIEnv *env, jobject obj, jint nObjID, jint protectType, jstring protectPwd);

JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_openData(JNIEnv *, jobject, jbyteArray);
JNIEXPORT jbyteArray JNICALL Java_srvSeal_SrvSealUtil_getData(JNIEnv *env, jobject obj, jint nObjID);

JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_getPageImg(JNIEnv *env, jobject obj, jint nObjID, jint pageNo, jint pageW, jstring imgPath, jstring imgType);

JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_decFile(JNIEnv *env, jobject obj, jstring pcInFile, jstring pcOutFile, jstring pcPwd);
JNIEXPORT jstring JNICALL Java_srvSeal_SrvSealUtil_encFile(JNIEnv *env, jobject obj, jint nObjID, jstring pcInFile, jstring pcOutFile);

//nType=0:word, =1:excel, =2:ppt
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_openOffice(JNIEnv *env, jobject obj, jint nType);
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_officeToPdf(JNIEnv *env, jobject obj, jint nOfficeID, jstring pcInFile, jstring pcOutFile);
JNIEXPORT jint JNICALL Java_srvSeal_SrvSealUtil_closeOffice(JNIEnv *env, jobject obj, jint nOfficeID);

#ifdef __cplusplus
}
#endif
#endif

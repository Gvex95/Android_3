#include "CalculatePercente.h"
#include "jni.h"

JNIEXPORT jfloat JNICALL Java_ra20_12014_taskmanager_NativeClass_calculatePercent
  (JNIEnv *env, jobject obj, jfloat finished_tasks, jfloat number_of_tasks)
  {
	return (jfloat) ((finished_tasks/number_of_tasks) * 100);
  }
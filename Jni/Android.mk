LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libCalculatePercante
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := CalculatePercente.c

include $(BUILD_SHARED_LIBRARY)
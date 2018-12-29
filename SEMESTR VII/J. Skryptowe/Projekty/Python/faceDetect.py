#!/usr/bin/python
# Krzysztof Kozubek
import os
import sys
import getopt
import imp
from os.path import basename

def help():
	print "Face detect\n\tThis script is based on library OpenCV. \n\tSo if you don't have library OpenCV you should lunch script \n\twith argument -i or --install to install it.\n\tScript using set learning to find faces on the video or picter.\nVideo is converter to frames. On each frame program looking for a faces.\nAfter checked, on frame or picture is painting square where was recognised a face.\nIn case video result is already showed but in picters result is saved on disk in file face-detect-fileName \nOptions:\n\t-h --help \t\thelp\n\t-i --install \t\tinstall lib opencv-python\n\t-c --camera \t\tuse camera to find face\n\t-p --photo [file image] use file image to find a face\n\n"

def check_import():
	try:
		global cv2
		import cv2
	except ImportError:
		print "\nYou have not a library OpenCV\n"
		help()
		sys.exit()


def detect(path):
    print "\nDetect face on photo {} \n\n".format(path)
    img = cv2.imread(path)

    if img is None:
	print "Cannot use this file to recognition a face"
        sys.exit()
    cascade = cv2.CascadeClassifier(os.path.abspath(os.path.dirname(sys.argv[0]))+"/face.xml")
    rects = cascade.detectMultiScale(img, 1.2, 7)#1.3, 4, cv2.cv.CV_HAAR_SCALE_IMAGE, (20,20))
    if len(rects) == 0:
        return [], img
    rects[:, 2:] += rects[:, :2]
    
    return rects, img

def box(rects, img, name):
	for x1, y1, x2, y2 in rects:
		cv2.rectangle(img, (x1, y1), (x2, y2), (127, 255, 0), 2)
	cv2.imwrite('face-detect-'+basename(name), img)
	cv2.imshow("image-face-detect.jpg", img)
	cv2.waitKey()

	sys.exit()

def detect_from_camera():
	print "\nDetect face on camera\n\n"
	face_cascade = cv2.CascadeClassifier(os.path.abspath(os.path.dirname(sys.argv[0]))+"/face.xml")
	cap = cv2.VideoCapture(0)

	while 1:
	    ret, img = cap.read()
	    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
	    faces = face_cascade.detectMultiScale(gray, 1.2, 7)

	    for (x,y,w,h) in faces:
	        cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
	        roi_gray = gray[y:y+h, x:x+w]
	        roi_color = img[y:y+h, x:x+w]

	    cv2.imshow('img',img)
	    k = cv2.waitKey(1) & 0xff
	    if k == 27:
	        break

	cap.release()
	cv2.destroyAllWindows()

def install_opencv():
	print "\nRozpoczynam instalacje\n\n"
	os.system("sudo apt-get install python-opencv")


def main(argv):
	print 
	try:
		opts, args = getopt.getopt(argv,"hcip:",["help","camera","install","photo="])
	except getopt.GetoptError:
		print "\nZla skladnia!\n\n" 
		help()
		sys.exit(2)
	for opt, arg in opts:
		if opt in ("-h", "--help"):   
			print "\nHelp\n\n" 
			help()
			sys.exit()
		elif opt in ("-c", "--camera"):
			check_import()
			detect_from_camera()
		elif opt in ("-p", "--photo"):
			check_import()
			rects, img = detect(argv[1])
			box(rects, img, argv[1])
		elif opt in ("-i", "--install"):
			install_opencv()

if __name__ == "__main__":
   main(sys.argv[1:])

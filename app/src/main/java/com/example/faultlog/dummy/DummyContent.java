package com.example.faultlog.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent implements Parcelable {



        public String id = "";
        public String zone = "";
        public String feeder = "";
        public String isolatedDt= "";
        public String voltageLvl= "";
        public String capacity= "";
        public String detailsOfMaintenance= "";
        public String restorationDate= "";
        public String areaAffected= "";
        public String durationOfOutage= "";
        public String actionTaken= "";
        public String timeIn= "";
        public String remarks= "";
        public String log= "";



    public DummyContent(String id, String timeIn, String zone, String feeder, String isolatedDt, String voltageLvl, String capacity, String detailsOfMaintenance, String durationOfOutage, String actionTaken, String remarks) {
            this.id = id;
            this.timeIn = timeIn;
            this.zone = zone;
            this.feeder = feeder;
            this.isolatedDt = isolatedDt;
            this.voltageLvl = voltageLvl;
            this.capacity = capacity;
            this.detailsOfMaintenance = detailsOfMaintenance;
            this.durationOfOutage = durationOfOutage;
            this.actionTaken = actionTaken;
            this.remarks = remarks;
        }

        public DummyContent(String id, String zone, String feeder, String isolatedDt,
                            String voltageLvl, String capacity, String detailsOfMaintenance,
                            String restorationDate, String areaAffected, String durationOfOutage,
                            String actionTaken, String timeIn, String remarks) {
                this.id = id;
                this.zone = zone;
                this.feeder = feeder;
                this.isolatedDt = isolatedDt;
                this.voltageLvl = voltageLvl;
                this.capacity = capacity;
                this.detailsOfMaintenance = detailsOfMaintenance;
                this.restorationDate = restorationDate;
                this.areaAffected = areaAffected;
                this.durationOfOutage = durationOfOutage;
                this.actionTaken = actionTaken;
                this.timeIn = timeIn;
                this.remarks = remarks;
        }

        public DummyContent() {
    }


        protected DummyContent(Parcel in) {
                id = in.readString();
                zone = in.readString();
                feeder = in.readString();
                isolatedDt = in.readString();
                voltageLvl = in.readString();
                capacity = in.readString();
                detailsOfMaintenance = in.readString();
                restorationDate = in.readString();
                areaAffected = in.readString();
                durationOfOutage = in.readString();
                actionTaken = in.readString();
                timeIn = in.readString();
                remarks = in.readString();
                log = in.readString();
        }

        public static final Creator<DummyContent> CREATOR = new Creator<DummyContent>() {
                @Override
                public DummyContent createFromParcel(Parcel in) {
                        return new DummyContent(in);
                }

                @Override
                public DummyContent[] newArray(int size) {
                        return new DummyContent[size];
                }
        };

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(zone);
                dest.writeString(feeder);
                dest.writeString(isolatedDt);
                dest.writeString(voltageLvl);
                dest.writeString(capacity);
                dest.writeString(detailsOfMaintenance);
                dest.writeString(restorationDate);
                dest.writeString(areaAffected);
                dest.writeString(durationOfOutage);
                dest.writeString(actionTaken);
                dest.writeString(timeIn);
                dest.writeString(remarks);
                dest.writeString(log);
        }
}
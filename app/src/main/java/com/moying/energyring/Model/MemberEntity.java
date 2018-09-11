package com.moying.energyring.Model;

/**
 * Created by waylen on 2018/8/24.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by adinnet on 2016/1/22.
 *  通讯录用户
 */
public class MemberEntity implements Parcelable {

    private String id;
    private String last_update;
    private String user_name;
    private String phone;
    private String sortLetters;
//	private String name;

    public String getId() {
        return id;
    }

    public MemberEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getLast_update() {
        return last_update;
    }

    public MemberEntity setLast_update(String last_update) {
        this.last_update = last_update;
        return this;
    }

    public String getUser_name() {
        return user_name;
    }

    public MemberEntity setUser_name(String user_name) {
        this.user_name = user_name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

//	public String getName() {
//		return name;
//	}
//
//	public MemberEntity setName(String name) {
//		this.name = name;
//		return this;
//	}

    public MemberEntity setPhone(String phone) {
        this.phone = phone;
        if (TextUtils.isEmpty(user_name)) {
            this.user_name = this.phone;
        }
        return this;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(last_update);
        dest.writeString(user_name);
        dest.writeString(phone);
        dest.writeString(sortLetters);
//		dest.writeString(name);
    }

    public static final Parcelable.Creator<MemberEntity> CREATOR = new Creator<MemberEntity>() {

        @Override
        public MemberEntity[] newArray(int size) {
            return new MemberEntity[size];
        }

        @Override
        public MemberEntity createFromParcel(Parcel source) {
            MemberEntity info = new MemberEntity();
            info.setId(source.readString());
            info.setLast_update(source.readString());
            info.setUser_name(source.readString());
            info.setPhone(source.readString());
            info.setSortLetters(source.readString());
//			info.setName(source.readString());
            return info;
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final MemberEntity other = (MemberEntity) obj;
        if (!this.getPhone().equals(other.getPhone())) {
            return false;
        }
        return true;
    }
}

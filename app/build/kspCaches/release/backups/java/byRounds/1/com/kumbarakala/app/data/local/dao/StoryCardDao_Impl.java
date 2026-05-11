package com.kumbarakala.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.kumbarakala.app.data.model.StoryCard;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StoryCardDao_Impl implements StoryCardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StoryCard> __insertionAdapterOfStoryCard;

  private final EntityDeletionOrUpdateAdapter<StoryCard> __updateAdapterOfStoryCard;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfIncrementShareCount;

  public StoryCardDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStoryCard = new EntityInsertionAdapter<StoryCard>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `story_cards` (`id`,`product_id`,`artisan_id`,`benefit_text`,`health_fact`,`template_style`,`card_image_path`,`share_count`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StoryCard entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindLong(3, entity.getArtisanId());
        statement.bindString(4, entity.getBenefitText());
        statement.bindString(5, entity.getHealthFact());
        statement.bindString(6, entity.getTemplateStyle());
        statement.bindString(7, entity.getCardImagePath());
        statement.bindLong(8, entity.getShareCount());
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__updateAdapterOfStoryCard = new EntityDeletionOrUpdateAdapter<StoryCard>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `story_cards` SET `id` = ?,`product_id` = ?,`artisan_id` = ?,`benefit_text` = ?,`health_fact` = ?,`template_style` = ?,`card_image_path` = ?,`share_count` = ?,`created_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StoryCard entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindLong(3, entity.getArtisanId());
        statement.bindString(4, entity.getBenefitText());
        statement.bindString(5, entity.getHealthFact());
        statement.bindString(6, entity.getTemplateStyle());
        statement.bindString(7, entity.getCardImagePath());
        statement.bindLong(8, entity.getShareCount());
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM story_cards WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementShareCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE story_cards SET share_count = share_count + 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final StoryCard storyCard, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStoryCard.insertAndReturnId(storyCard);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final StoryCard storyCard, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStoryCard.handle(storyCard);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementShareCount(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementShareCount.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementShareCount.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final int id, final Continuation<? super StoryCard> $completion) {
    final String _sql = "SELECT * FROM story_cards WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StoryCard>() {
      @Override
      @Nullable
      public StoryCard call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfArtisanId = CursorUtil.getColumnIndexOrThrow(_cursor, "artisan_id");
          final int _cursorIndexOfBenefitText = CursorUtil.getColumnIndexOrThrow(_cursor, "benefit_text");
          final int _cursorIndexOfHealthFact = CursorUtil.getColumnIndexOrThrow(_cursor, "health_fact");
          final int _cursorIndexOfTemplateStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "template_style");
          final int _cursorIndexOfCardImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "card_image_path");
          final int _cursorIndexOfShareCount = CursorUtil.getColumnIndexOrThrow(_cursor, "share_count");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final StoryCard _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            final int _tmpArtisanId;
            _tmpArtisanId = _cursor.getInt(_cursorIndexOfArtisanId);
            final String _tmpBenefitText;
            _tmpBenefitText = _cursor.getString(_cursorIndexOfBenefitText);
            final String _tmpHealthFact;
            _tmpHealthFact = _cursor.getString(_cursorIndexOfHealthFact);
            final String _tmpTemplateStyle;
            _tmpTemplateStyle = _cursor.getString(_cursorIndexOfTemplateStyle);
            final String _tmpCardImagePath;
            _tmpCardImagePath = _cursor.getString(_cursorIndexOfCardImagePath);
            final int _tmpShareCount;
            _tmpShareCount = _cursor.getInt(_cursorIndexOfShareCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new StoryCard(_tmpId,_tmpProductId,_tmpArtisanId,_tmpBenefitText,_tmpHealthFact,_tmpTemplateStyle,_tmpCardImagePath,_tmpShareCount,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StoryCard>> getAllByArtisan(final int artisanId) {
    final String _sql = "SELECT * FROM story_cards WHERE artisan_id = ? ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, artisanId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"story_cards"}, new Callable<List<StoryCard>>() {
      @Override
      @NonNull
      public List<StoryCard> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfArtisanId = CursorUtil.getColumnIndexOrThrow(_cursor, "artisan_id");
          final int _cursorIndexOfBenefitText = CursorUtil.getColumnIndexOrThrow(_cursor, "benefit_text");
          final int _cursorIndexOfHealthFact = CursorUtil.getColumnIndexOrThrow(_cursor, "health_fact");
          final int _cursorIndexOfTemplateStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "template_style");
          final int _cursorIndexOfCardImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "card_image_path");
          final int _cursorIndexOfShareCount = CursorUtil.getColumnIndexOrThrow(_cursor, "share_count");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<StoryCard> _result = new ArrayList<StoryCard>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StoryCard _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            final int _tmpArtisanId;
            _tmpArtisanId = _cursor.getInt(_cursorIndexOfArtisanId);
            final String _tmpBenefitText;
            _tmpBenefitText = _cursor.getString(_cursorIndexOfBenefitText);
            final String _tmpHealthFact;
            _tmpHealthFact = _cursor.getString(_cursorIndexOfHealthFact);
            final String _tmpTemplateStyle;
            _tmpTemplateStyle = _cursor.getString(_cursorIndexOfTemplateStyle);
            final String _tmpCardImagePath;
            _tmpCardImagePath = _cursor.getString(_cursorIndexOfCardImagePath);
            final int _tmpShareCount;
            _tmpShareCount = _cursor.getInt(_cursorIndexOfShareCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new StoryCard(_tmpId,_tmpProductId,_tmpArtisanId,_tmpBenefitText,_tmpHealthFact,_tmpTemplateStyle,_tmpCardImagePath,_tmpShareCount,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<StoryCard>> getByProductId(final int productId) {
    final String _sql = "SELECT * FROM story_cards WHERE product_id = ? ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"story_cards"}, new Callable<List<StoryCard>>() {
      @Override
      @NonNull
      public List<StoryCard> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfArtisanId = CursorUtil.getColumnIndexOrThrow(_cursor, "artisan_id");
          final int _cursorIndexOfBenefitText = CursorUtil.getColumnIndexOrThrow(_cursor, "benefit_text");
          final int _cursorIndexOfHealthFact = CursorUtil.getColumnIndexOrThrow(_cursor, "health_fact");
          final int _cursorIndexOfTemplateStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "template_style");
          final int _cursorIndexOfCardImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "card_image_path");
          final int _cursorIndexOfShareCount = CursorUtil.getColumnIndexOrThrow(_cursor, "share_count");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<StoryCard> _result = new ArrayList<StoryCard>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StoryCard _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            final int _tmpArtisanId;
            _tmpArtisanId = _cursor.getInt(_cursorIndexOfArtisanId);
            final String _tmpBenefitText;
            _tmpBenefitText = _cursor.getString(_cursorIndexOfBenefitText);
            final String _tmpHealthFact;
            _tmpHealthFact = _cursor.getString(_cursorIndexOfHealthFact);
            final String _tmpTemplateStyle;
            _tmpTemplateStyle = _cursor.getString(_cursorIndexOfTemplateStyle);
            final String _tmpCardImagePath;
            _tmpCardImagePath = _cursor.getString(_cursorIndexOfCardImagePath);
            final int _tmpShareCount;
            _tmpShareCount = _cursor.getInt(_cursorIndexOfShareCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new StoryCard(_tmpId,_tmpProductId,_tmpArtisanId,_tmpBenefitText,_tmpHealthFact,_tmpTemplateStyle,_tmpCardImagePath,_tmpShareCount,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCount(final int artisanId, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM story_cards WHERE artisan_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, artisanId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalShares(final int artisanId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COALESCE(SUM(share_count), 0) FROM story_cards WHERE artisan_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, artisanId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

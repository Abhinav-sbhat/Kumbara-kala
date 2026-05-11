package com.kumbarakala.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.kumbarakala.app.data.local.dao.ArtisanDao;
import com.kumbarakala.app.data.local.dao.ArtisanDao_Impl;
import com.kumbarakala.app.data.local.dao.ProductDao;
import com.kumbarakala.app.data.local.dao.ProductDao_Impl;
import com.kumbarakala.app.data.local.dao.StoryCardDao;
import com.kumbarakala.app.data.local.dao.StoryCardDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ArtisanDao _artisanDao;

  private volatile ProductDao _productDao;

  private volatile StoryCardDao _storyCardDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `artisans` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `phone` TEXT NOT NULL, `village` TEXT NOT NULL, `password_hash` TEXT NOT NULL, `bio` TEXT NOT NULL, `profile_photo` TEXT NOT NULL, `experience` TEXT NOT NULL, `specialty` TEXT NOT NULL, `created_at` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `artisan_id` INTEGER NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `description` TEXT NOT NULL, `photo_path` TEXT NOT NULL, `price` TEXT NOT NULL, `created_at` INTEGER NOT NULL, FOREIGN KEY(`artisan_id`) REFERENCES `artisans`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_products_artisan_id` ON `products` (`artisan_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `story_cards` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `product_id` INTEGER NOT NULL, `artisan_id` INTEGER NOT NULL, `benefit_text` TEXT NOT NULL, `health_fact` TEXT NOT NULL, `template_style` TEXT NOT NULL, `card_image_path` TEXT NOT NULL, `share_count` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, FOREIGN KEY(`product_id`) REFERENCES `products`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`artisan_id`) REFERENCES `artisans`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_story_cards_product_id` ON `story_cards` (`product_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_story_cards_artisan_id` ON `story_cards` (`artisan_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b1c187e4979eb544902193425eb6f699')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `artisans`");
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `story_cards`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsArtisans = new HashMap<String, TableInfo.Column>(11);
        _columnsArtisans.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("village", new TableInfo.Column("village", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("password_hash", new TableInfo.Column("password_hash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("bio", new TableInfo.Column("bio", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("profile_photo", new TableInfo.Column("profile_photo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("experience", new TableInfo.Column("experience", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("specialty", new TableInfo.Column("specialty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArtisans.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysArtisans = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesArtisans = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoArtisans = new TableInfo("artisans", _columnsArtisans, _foreignKeysArtisans, _indicesArtisans);
        final TableInfo _existingArtisans = TableInfo.read(db, "artisans");
        if (!_infoArtisans.equals(_existingArtisans)) {
          return new RoomOpenHelper.ValidationResult(false, "artisans(com.kumbarakala.app.data.model.Artisan).\n"
                  + " Expected:\n" + _infoArtisans + "\n"
                  + " Found:\n" + _existingArtisans);
        }
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(8);
        _columnsProducts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("artisan_id", new TableInfo.Column("artisan_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("photo_path", new TableInfo.Column("photo_path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("price", new TableInfo.Column("price", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysProducts.add(new TableInfo.ForeignKey("artisans", "CASCADE", "NO ACTION", Arrays.asList("artisan_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(1);
        _indicesProducts.add(new TableInfo.Index("index_products_artisan_id", false, Arrays.asList("artisan_id"), Arrays.asList("ASC")));
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(com.kumbarakala.app.data.model.Product).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsStoryCards = new HashMap<String, TableInfo.Column>(9);
        _columnsStoryCards.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryCards.put("product_id", new TableInfo.Column("product_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryCards.put("artisan_id", new TableInfo.Column("artisan_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryCards.put("benefit_text", new TableInfo.Column("benefit_text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryCards.put("health_fact", new TableInfo.Column("health_fact", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryCards.put("template_style", new TableInfo.Column("template_style", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryCards.put("card_image_path", new TableInfo.Column("card_image_path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryCards.put("share_count", new TableInfo.Column("share_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryCards.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStoryCards = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysStoryCards.add(new TableInfo.ForeignKey("products", "CASCADE", "NO ACTION", Arrays.asList("product_id"), Arrays.asList("id")));
        _foreignKeysStoryCards.add(new TableInfo.ForeignKey("artisans", "CASCADE", "NO ACTION", Arrays.asList("artisan_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesStoryCards = new HashSet<TableInfo.Index>(2);
        _indicesStoryCards.add(new TableInfo.Index("index_story_cards_product_id", false, Arrays.asList("product_id"), Arrays.asList("ASC")));
        _indicesStoryCards.add(new TableInfo.Index("index_story_cards_artisan_id", false, Arrays.asList("artisan_id"), Arrays.asList("ASC")));
        final TableInfo _infoStoryCards = new TableInfo("story_cards", _columnsStoryCards, _foreignKeysStoryCards, _indicesStoryCards);
        final TableInfo _existingStoryCards = TableInfo.read(db, "story_cards");
        if (!_infoStoryCards.equals(_existingStoryCards)) {
          return new RoomOpenHelper.ValidationResult(false, "story_cards(com.kumbarakala.app.data.model.StoryCard).\n"
                  + " Expected:\n" + _infoStoryCards + "\n"
                  + " Found:\n" + _existingStoryCards);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b1c187e4979eb544902193425eb6f699", "4609dbe3984ed544f1ddf47e0c6ed684");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "artisans","products","story_cards");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `artisans`");
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `story_cards`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ArtisanDao.class, ArtisanDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProductDao.class, ProductDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StoryCardDao.class, StoryCardDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ArtisanDao artisanDao() {
    if (_artisanDao != null) {
      return _artisanDao;
    } else {
      synchronized(this) {
        if(_artisanDao == null) {
          _artisanDao = new ArtisanDao_Impl(this);
        }
        return _artisanDao;
      }
    }
  }

  @Override
  public ProductDao productDao() {
    if (_productDao != null) {
      return _productDao;
    } else {
      synchronized(this) {
        if(_productDao == null) {
          _productDao = new ProductDao_Impl(this);
        }
        return _productDao;
      }
    }
  }

  @Override
  public StoryCardDao storyCardDao() {
    if (_storyCardDao != null) {
      return _storyCardDao;
    } else {
      synchronized(this) {
        if(_storyCardDao == null) {
          _storyCardDao = new StoryCardDao_Impl(this);
        }
        return _storyCardDao;
      }
    }
  }
}
